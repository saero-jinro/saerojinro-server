package lecture.application;

import static goorm.saerojinro.common.domain.BaseRole.*;
import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeUserRepository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LectureCommandServiceTest {

	private LectureCommandService lectureCommandService;
	private LectureQueryService lectureQueryService;
	private FakeLectureRepository lectureRepository;
	private FakeUserRepository userRepository;

	private static final String TITLE = "Lecture Title";
	private static final String CONTENTS = "Lecture Contents";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "room A";
	private static final Category CATEGORY = Category.BACKEND;
	private static final LectureStatus EXPECTED_STATUS = LectureStatus.PENDING_APPROVAL;

	private static final User VALID_SPEAKER = User.builder()
		.id(1L)
		.name("Speaker")
		.role(SPEAKER)
		.build();

	@BeforeEach
	void setUp() {
		lectureRepository = new FakeLectureRepository();
		userRepository = new FakeUserRepository();
		lectureQueryService = new LectureQueryService(lectureRepository);
		lectureCommandService = new LectureCommandService(lectureRepository);
		userRepository.save(VALID_SPEAKER);
	}

	@Test
	@DisplayName("정상적으로 강의를 생성한다")
	void createLecture_success() {
		// when
		Lecture createdLecture = lectureCommandService.create(
			VALID_SPEAKER, TITLE, CONTENTS, MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// then
		assertNotNull(createdLecture);
		assertEquals(TITLE, createdLecture.getTitle());
		assertEquals(CONTENTS, createdLecture.getContents());
		assertEquals(MAX_CAPACITY, createdLecture.getMaxCapacity());
		assertEquals(START_TIME, createdLecture.getStartTime());
		assertEquals(END_TIME, createdLecture.getEndTime());
		assertEquals(LOCATION, createdLecture.getLocation());
		assertEquals(CATEGORY, createdLecture.getCategory());
		assertEquals(EXPECTED_STATUS, createdLecture.getLectureStatus());

		assertNotNull(createdLecture.getSpeaker());
		assertEquals(VALID_SPEAKER.getId(), createdLecture.getSpeaker().getId());
	}

	@Test
	@DisplayName("정상적으로 강의를 수정한다")
	void updateLecture_success() {
		//given
		Lecture createdLecture = lectureCommandService.create(
			VALID_SPEAKER, TITLE, CONTENTS, MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// when
		Lecture findLecture = lectureQueryService.getByLectureId(createdLecture.getId());
		lectureCommandService.update(VALID_SPEAKER, findLecture.getId(), "updated title", "updated contents");

		// then
		assertNotNull(createdLecture);

		assertEquals(createdLecture.getId(), 1L);
		assertEquals("updated title", createdLecture.getTitle());
		assertEquals("updated contents", createdLecture.getContents());
		assertEquals(MAX_CAPACITY, createdLecture.getMaxCapacity());
		assertEquals(START_TIME, createdLecture.getStartTime());
		assertEquals(END_TIME, createdLecture.getEndTime());
		assertEquals(LOCATION, createdLecture.getLocation());
		assertEquals(CATEGORY, createdLecture.getCategory());
		assertEquals(EXPECTED_STATUS, createdLecture.getLectureStatus());
		assertEquals(createdLecture.getSpeaker(), VALID_SPEAKER);
	}

	@Test
	@DisplayName("정상적으로 강의를 삭제한다")
	void deleteLecture_success() {
		//given
		Lecture createdLecture = lectureCommandService.create(
			VALID_SPEAKER, TITLE, CONTENTS, MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// when
		Lecture findLecture = lectureQueryService.getByLectureId(createdLecture.getId());
		lectureCommandService.delete(VALID_SPEAKER, findLecture.getId());

		// then
		assertNotNull(findLecture);

		assertEquals(LectureStatus.PENDING_DELETION, findLecture.getLectureStatus());
	}
}
