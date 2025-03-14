package lecture;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.domain.Category.*;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_RESERVATION_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.api.lecture.application.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureSummaryListResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.application.LectureRecommendationService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import goorm.saerojinro.domain.logevent.application.LogEventService;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.producer.FakeLogEventProducer;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeLogEventRepository;
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
	private final FakeLogEventProducer fakeEventLogProducer = new FakeLogEventProducer();

	private Lecture lecture1;
	private Lecture lecture2;

	private static final String NAME = "Cole Palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 CEO";
	private static final String INTRODUCTION = "AA 기업  - 백엔드 개발";
	private static final String FILMOGRAPHY = "Location";
	private static final String IMAGE_URI = "uploads/speaker";

	@BeforeEach
	void setUp() {
		FakeLectureRepository lectureRepository = new FakeLectureRepository();
		lectureQueryService = new LectureQueryService(lectureRepository);
		FakeLogEventRepository fakeLogEventRepository = new FakeLogEventRepository();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		UserQueryService userQueryService = new UserQueryService(fakeUserRepository, new BCryptPasswordEncoder());
		LogEventService logEventService = new LogEventService(fakeLogEventRepository, userQueryService, lectureQueryService);
		lectureFacade = new LectureFacade(lectureQueryService, fakeEventLogProducer, userQueryService, new LectureRecommendationService(), logEventService);

		Speaker speaker = Speaker.builder()
			.name(NAME)
			.email(EMAIL)
			.position(POSITION)
			.introduction(INTRODUCTION)
			.filmography(FILMOGRAPHY)
			.imageUri(IMAGE_URI)
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
			.startTime(LocalDateTime.of(2025, 3, 1, 10, 0))
			.endTime(LocalDateTime.of(2025, 3, 1, 12, 0))
			.location("room B")
			.category(FRONTEND)
			.build();

		User user1 = fakeUserRepository.save(User.builder()
			.email("email@email.com")
			.password("password1234!")
			.name("박민준")
			.role(ADMIN)
			.build()
		);

		lecture1 = lectureRepository.save(lecture1);
		lecture2 = lectureRepository.save(lecture2);

		String record = "record";
		RedisLogEvent redisLogEvent1 = RedisLogEvent.of(user1.getId(), lecture1.getId(), LECTURE_RESERVATION_SUCCESS, lecture1.getCategory());
		RedisLogEvent redisLogEvent2 = RedisLogEvent.of(user1.getId(), lecture1.getId(), LECTURE_RESERVATION_SUCCESS, lecture1.getCategory());
		RedisLogEvent redisLogEvent3 = RedisLogEvent.of(user1.getId(), lecture2.getId(), LECTURE_RESERVATION_SUCCESS, lecture2.getCategory());

		logEventService.save(record + 1, redisLogEvent1);
		logEventService.save(record + 2, redisLogEvent2);
		logEventService.save(record + 3, redisLogEvent3);

		UserDetails user = userQueryService.getByEmail("email@email.com");
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
		);
	}

	@Test
	@DisplayName("강의 아이디로 강의 상세 정보를 조회할 수 있다")
	void getById_success() {
		LectureDetailResponse detail = lectureFacade.getById(1L);

		assertNotNull(detail);
		assertEquals(lecture1.getTitle(), detail.title());
		assertEquals(lecture1.getContents(), detail.contents());
	}

	@Test
	@DisplayName("존재하지 않는 강의 ID면 예외를 반환한다")
	void getByLectureId_notFound() {
		assertThrows(LectureNotFoundException.class, () -> lectureQueryService.getById(999L));
	}

	@Test
	@DisplayName("주어진 날짜에 해당하는 강의를 조회할 수 있다")
	void getByDate_success() {
		LocalDate date = LocalDate.of(2025, 3, 1);

		// when
		LectureListResponse response = lectureFacade.getByDate(date);

		// then
		assertNotNull(response);
		assertEquals(2, response.lectures().size());
		assertEquals(lecture1.getTitle(), response.lectures().get(0).title());
		assertEquals(lecture2.getTitle(), response.lectures().get(1).title());
	}

	@Test
	@DisplayName("getRecommendationLectures는 우선순위 대로 조회된 List가 반환된다.")
	void getRecommendationLectures_Success() {
		// given
		LocalDateTime startTime = LocalDateTime.of(2025, 3, 1, 10, 0);

		// when
		LectureSummaryListResponse response = lectureFacade.getRecommendationLectures(startTime);

		// then
		assertEquals(2, response.responses().size());
		assertEquals(lecture1.getTitle(), response.responses().get(0).title());
		assertEquals(lecture2.getTitle(), response.responses().get(1).title());
	}
}
