package speaker.api;

import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.lecture.exception.LectureDeleteNotAuthorizedException;
import goorm.saerojinro.domain.lecture.exception.LectureUpdateNotAuthorizedException;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.speaker.api.application.SpeakerLectureFacade;
import goorm.saerojinro.speaker.api.presentation.request.LectureCreateRequest;
import goorm.saerojinro.speaker.api.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.speaker.api.presentation.response.LectureCreateResponse;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeUserRepository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SpeakerLectureFacadeTest {

	private SpeakerLectureFacade speakerLectureFacade;
	private FakeLectureRepository lectureRepository;
	private FakeUserRepository userRepository;

	private static final Long VALID_SPEAKER_ID = 1L;
	private static final Long VALID_SPEAKER_2_ID = 2L;
	private static final String TITLE = "Lecture Title";
	private static final String CONTENTS = "Lecture Contents";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "room A";
	private static final Category CATEGORY = Category.BACKEND;

	private static final User VALID_SPEAKER = User.builder()
		.id(VALID_SPEAKER_ID)
		.name("Test Speaker")
		.role(BaseRole.SPEAKER)
		.build();

	private static final User VALID_SPEAKER_2 = User.builder()
		.id(VALID_SPEAKER_2_ID)
		.name("Test Speaker 2")
		.role(BaseRole.SPEAKER)
		.build();

	@BeforeEach
	public void setUp() {
		lectureRepository = new FakeLectureRepository();
		userRepository = new FakeUserRepository();
		LectureCommandService lectureCommandService = new LectureCommandService(lectureRepository);
		UserQueryService userQueryService = new UserQueryService(userRepository, new BCryptPasswordEncoder());

		speakerLectureFacade = new SpeakerLectureFacade(lectureCommandService, userQueryService);

		userRepository.save(VALID_SPEAKER);
		userRepository.save(VALID_SPEAKER_2);
	}

	@Test
	@DisplayName("정상적으로 강의를 생성한다")
	void createLecture_success() {
		// given
		LectureCreateRequest request = LectureCreateRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME)
			.endTime(END_TIME)
			.location(LOCATION)
			.category(CATEGORY)
			.build();

		// when
		LectureCreateResponse response = speakerLectureFacade.create(VALID_SPEAKER_ID, request);

		// then
		assertNotNull(response);
		assertTrue(response.lectureId() > 0);
	}

	@Test
	@DisplayName("정상적으로 강의를 수정한다")
	void updateLecture_success() {
		// given
		LectureCreateRequest createRequest = LectureCreateRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME)
			.endTime(END_TIME)
			.location(LOCATION)
			.category(CATEGORY)
			.build();

		LectureCreateResponse createResponse = speakerLectureFacade.create(VALID_SPEAKER_ID, createRequest);
		Long lectureId = createResponse.lectureId();

		LectureUpdateRequest updateRequest = LectureUpdateRequest.builder()
			.title("Updated Title")
			.contents("Updated Contents")
			.build();

		// when
		speakerLectureFacade.update(VALID_SPEAKER_ID, lectureId, updateRequest);

		// 강연자가 일치하지 않을 경우 예외 반환
		Assertions.assertThrows(LectureUpdateNotAuthorizedException.class, () ->
			speakerLectureFacade.update(2L, lectureId, updateRequest)
		);

		// then
		Lecture updatedLecture = lectureRepository.findById(lectureId)
			.orElseThrow();
		assertEquals("Updated Title", updatedLecture.getTitle());
		assertEquals("Updated Contents", updatedLecture.getContents());
		assertEquals(MAX_CAPACITY, updatedLecture.getMaxCapacity());
		assertEquals(START_TIME, updatedLecture.getStartTime());
		assertEquals(END_TIME, updatedLecture.getEndTime());
		assertEquals(LOCATION, updatedLecture.getLocation());
		assertEquals(CATEGORY, updatedLecture.getCategory());
		assertEquals(VALID_SPEAKER.getId(), updatedLecture.getSpeaker().getId());
	}

	@Test
	@DisplayName("정상적으로 강의를 삭제한다")
	void deleteLecture_success() {
		// given
		LectureCreateRequest createRequest = LectureCreateRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME)
			.endTime(END_TIME)
			.location(LOCATION)
			.category(CATEGORY)
			.build();

		LectureCreateResponse createResponse = speakerLectureFacade.create(VALID_SPEAKER_ID, createRequest);
		Long lectureId = createResponse.lectureId();

		// when
		speakerLectureFacade.delete(VALID_SPEAKER_ID, lectureId);

		// 강연자가 일치하지 않을 경우 예외 반환
		Assertions.assertThrows(LectureDeleteNotAuthorizedException.class, () ->
			speakerLectureFacade.delete((2L), createResponse.lectureId()
			));


		// then
		Lecture deletedLecture = lectureRepository.findById(lectureId)
			.orElseThrow();

		assertEquals(LectureStatus.PENDING_DELETION, deletedLecture.getLectureStatus());
	}
}
