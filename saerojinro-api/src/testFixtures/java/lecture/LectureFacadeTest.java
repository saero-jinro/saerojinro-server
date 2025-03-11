package lecture;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.domain.Category.*;
import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.api.lecture.application.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponseByAll;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponseByDate;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.producer.FakeLogEventProducer;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeUserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class LectureFacadeTest {

	private LectureFacade lectureFacade;
	private LectureQueryService lectureQueryService;
	private FakeLectureRepository lectureRepository;
	private FakeLogEventProducer fakeEventLogProducer = new FakeLogEventProducer();
	private UserQueryService userQueryService;

	private Lecture lecture1;
	private Lecture lecture2;

	private Speaker speaker;
	private Speaker speaker2;

	private static final String NAME = "Cole Palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String EMAIL_2 = "google_2@mail.com";
	private static final String POSITION = "00 기업 CEO";
	private static final String  INTRODUCTION = "AA 기업  - 백엔드 개발";
	private static final String FILMOGRAPHY = "Location";
	private static final String PHOTO = "Photo uri";


	@BeforeEach
	void setUp() {
		lectureRepository = new FakeLectureRepository();
		lectureQueryService = new LectureQueryService(lectureRepository);

		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		userQueryService = new UserQueryService(fakeUserRepository, new BCryptPasswordEncoder());
		lectureFacade = new LectureFacade(lectureQueryService, fakeEventLogProducer, userQueryService);

		speaker = Speaker.builder()
			.name(NAME)
			.email(EMAIL)
			.position(POSITION)
			.introduction(INTRODUCTION)
			.filmography(FILMOGRAPHY)
			.photo(PHOTO)
			.build();

		speaker2 = Speaker.builder()
			.name(NAME)
			.email(EMAIL_2)
			.position(POSITION)
			.introduction(INTRODUCTION)
			.filmography(FILMOGRAPHY)
			.photo(PHOTO)
			.build();

		lecture1 = Lecture.builder()
			.speaker(speaker)
			.title("Lecture One")
			.contents("Contents One")
			.maxCapacity(100L)
			.startTime(LocalDateTime.of(2025, 3, 1, 10, 0))
			.endTime(LocalDateTime.of(2025, 3, 1, 12, 0))
			.location("room A")
			.category(BACKEND)
			.build();

		lecture2 = Lecture.builder()
			.speaker(speaker)
			.title("Lecture Two")
			.contents("Contents Two")
			.maxCapacity(100L)
			.startTime(LocalDateTime.of(2025, 3, 1, 14, 0))
			.endTime(LocalDateTime.of(2025, 3, 1, 16, 0))
			.location("room B")
			.category(BACKEND)
			.build();

		lectureRepository.save(lecture1);
		lectureRepository.save(lecture2);

		fakeUserRepository.save(User.builder()
			.email("email@email.com")
			.password("password1234!")
			.name("박민준")
			.role(ADMIN)
			.build()
		);

		UserDetails user = userQueryService.getByEmail("email@email.com");
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
		);
	}

	@Test
	@DisplayName("전체 강의 목록을 리스트로 조회할 수 있다")
	void getAllLecture_success() {
		// when
		LectureListResponseByAll response = lectureFacade.getAllLecture();

		// then
		assertNotNull(response);
		assertEquals(2, response.totalCount());
		assertEquals(2, response.lectures().size());

		assertEquals("Lecture One", response.lectures().get(0).title());
		assertEquals("Cole Palmer", response.lectures().get(0).speakerName());
	}

	@Test
	@DisplayName("강의 아이디로 강의 상세 정보를 조회할 수 있다")
	void getByLectureId_success() {
		LectureDetailResponse detail = lectureFacade.getByLectureId(1L);

		assertNotNull(detail);
		assertEquals("Lecture One", detail.title());
		assertEquals("Contents One", detail.contents());
	}

	@Test
	@DisplayName("존재하지 않는 강의 ID면 예외를 반환한다")
	void getByLectureId_notFound() {
		assertThrows(LectureNotFoundException.class, () -> lectureQueryService.getByLectureId(999L));
	}

	@Test
	@DisplayName("주어진 날짜에 해당하는 강의를 조회할 수 있다")
	void getByDate_success() {
		// given: lecture1, lecture2 모두 2025-03-01에 시작
		LocalDate date = LocalDate.of(2025, 3, 1);

		// when
		LectureListResponseByDate response = lectureFacade.getByDate(date);

		// then
		assertNotNull(response);
		assertEquals(2, response.lectures().size());
		assertEquals("Lecture One", response.lectures().get(0).title());
		assertEquals("Lecture Two", response.lectures().get(1).title());
	}
}
