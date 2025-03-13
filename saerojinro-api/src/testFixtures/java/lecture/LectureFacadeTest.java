package lecture;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.domain.Category.*;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_RESERVATION_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.api.lecture.application.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureSummaryListResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.application.LectureRecommendationService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import goorm.saerojinro.domain.logevent.application.LogEventService;
import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import mock.producer.FakeLogEventProducer;
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
	private static final String POSITION = "00 기업 / CEO";
	private static final String INTRODUCTION = "안녕하세요 OO 기업 CEO OOO 입니다";
	private static final String FILMOGRAPHY = "AA 기업 - 백엔드 개발 담당";

	private static final String SPEAKER_IMAGE_URI = "uploads/speaker/123456.jpg";

	private static final String LECTURE_TITLE_ONE = "Lecture One";
	private static final String LECTURE_CONTENTS_ONE = "Contents One";
	private static final String LECTURE_TITLE_TWO = "Lecture Two";
	private static final String LECTURE_CONTENTS_TWO = "Contents Two";

	private static final String THUMBNAIL_URI_ONE = "uploads/lecture/thumbnail/123456.jpg";
	private static final String MATERIAL_URI_ONE = "uploads/lecture/materials/발표자료.pdf";

	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME_ONE = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME_ONE = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final LocalDateTime START_TIME_TWO = LocalDateTime.of(2025, 3, 1, 14, 0);
	private static final LocalDateTime END_TIME_TWO = LocalDateTime.of(2025, 3, 1, 16, 0);
	private static final String LOCATION_ONE = "room A";
	private static final String LOCATION_TWO = "room B";

	@BeforeEach
	void setUp() {
		FakeLectureRepository lectureRepository = new FakeLectureRepository();
		lectureQueryService = new LectureQueryService(lectureRepository);
		FakeLogEventRepository fakeLogEventRepository = new FakeLogEventRepository();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		UserQueryService userQueryService = new UserQueryService(fakeUserRepository, new BCryptPasswordEncoder());
		LogEventService logEventService = new LogEventService(fakeLogEventRepository, userQueryService, lectureQueryService);
		lectureFacade = new LectureFacade(lectureQueryService, fakeEventLogProducer, userQueryService, new LectureRecommendationService(), logEventService);

		File speakerImageFile = File.create("Speaker_Image", SPEAKER_IMAGE_URI, 3000L, "jpg");

		Speaker speaker = Speaker.builder()
			.name(NAME)
			.email(EMAIL)
			.position(POSITION)
			.introduction(INTRODUCTION)
			.filmography(FILMOGRAPHY)
			.imageFile(speakerImageFile)
			.build();

		File thumbnailFile1 = File.create("Thumbnail1", THUMBNAIL_URI_ONE, 5000L, "jpg");
		File materialFile1 = File.create("Material1", MATERIAL_URI_ONE, 10000L, "pdf");

		lecture1 = Lecture.builder()
			.speaker(speaker)
			.title(LECTURE_TITLE_ONE)
			.contents(LECTURE_CONTENTS_ONE)
			.thumbnailFile(thumbnailFile1)
			.materialFile(materialFile1)
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME_ONE)
			.endTime(END_TIME_ONE)
			.location(LOCATION_ONE)
			.category(BACKEND)
			.build();

		File thumbnailFile2 = File.create("Thumbnail2", THUMBNAIL_URI_ONE, 5000L, "jpg"); // 재사용 가능
		File materialFile2 = File.create("Material2", MATERIAL_URI_ONE, 10000L, "pdf");

		lecture2 = Lecture.builder()
			.speaker(speaker)
			.title(LECTURE_TITLE_TWO)
			.contents(LECTURE_CONTENTS_TWO)
			.thumbnailFile(thumbnailFile2)
			.materialFile(materialFile2)
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME_TWO)
			.endTime(END_TIME_TWO)
			.location(LOCATION_TWO)
			.category(BACKEND)
			.build();

		lecture1 = lectureRepository.save(lecture1);
		lecture2 = lectureRepository.save(lecture2);

		User user1 = fakeUserRepository.save(User.builder()
			.email("email@email.com")
			.password("password1234!")
			.name("박민준")
			.role(ADMIN)
			.build()
		);

		var userDetails = userQueryService.getByEmail("email@email.com");
		lecture1 = lectureRepository.save(lecture1);
		lecture2 = lectureRepository.save(lecture2);

		String record = "record";
		LogEventDto logEventDto1 = LogEventDto.of(user1.getId(), lecture1.getId(), LECTURE_RESERVATION_SUCCESS, lecture1.getCategory());
		LogEventDto logEventDto2 = LogEventDto.of(user1.getId(), lecture1.getId(), LECTURE_RESERVATION_SUCCESS, lecture1.getCategory());
		LogEventDto logEventDto3 = LogEventDto.of(user1.getId(), lecture2.getId(), LECTURE_RESERVATION_SUCCESS, lecture2.getCategory());

		logEventService.save(record + 1, logEventDto1);
		logEventService.save(record + 2, logEventDto2);
		logEventService.save(record + 3, logEventDto3);

		UserDetails user = userQueryService.getByEmail("email@email.com");
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities())
		);
	}

	@Test
	@DisplayName("강의 아이디로 강의 상세 정보를 조회할 수 있다")
	void getById_success() {
		LectureDetailResponse detail = lectureFacade.getById(lecture1.getId());

		assertNotNull(detail);
		assertEquals(lecture1.getTitle(), detail.title());
		assertEquals(lecture1.getContents(), detail.contents());
	}

	@Test
	@DisplayName("존재하지 않는 강의 ID면 예외를 반환한다")
	void getByLectureId_notFound() {
		assertThrows(LectureNotFoundException.class, () -> lectureFacade.getById(999L));
	}

	@Test
	@DisplayName("주어진 날짜에 해당하는 강의를 조회할 수 있다")
	void getByDate_success() {
		LocalDate date = LocalDate.of(2025, 3, 1);

		// when
		LectureListResponse response = lectureFacade.getByDate(date);

		// then
		assertNotNull(response);
		assertEquals(4, response.lectures().size());
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
	}
}
