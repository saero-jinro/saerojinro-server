package lecture.application;

import static goorm.saerojinro.common.domain.BaseRole.*;
import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.exception.InvalidUserRoleException;
import goorm.saerojinro.domain.user.exception.UserNotFoundException;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeUserRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LectureCommandServiceTest {

	private LectureCommandService lectureCommandService;
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
		lectureCommandService = new LectureCommandService(lectureRepository);

		userRepository.save(VALID_SPEAKER);
	}

	@Test
	@DisplayName("정상적으로 강의를 생성한다")
	void createLecture_success() {
		// when
		Lecture createdLecture = lectureCommandService.createLecture(
			VALID_SPEAKER, TITLE, CONTENTS, MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// then
		assertNotNull(createdLecture, "생성된 Lecture 객체는 null이면 안 됩니다.");
		assertEquals(TITLE, createdLecture.getTitle());
		assertEquals(CONTENTS, createdLecture.getContents());
		assertEquals(MAX_CAPACITY, createdLecture.getMaxCapacity());
		assertEquals(START_TIME, createdLecture.getStartTime());
		assertEquals(END_TIME, createdLecture.getEndTime());
		assertEquals(LOCATION, createdLecture.getLocation());
		assertEquals(CATEGORY, createdLecture.getCategory());
		assertEquals(EXPECTED_STATUS, createdLecture.getLectureStatus());

		assertNotNull(createdLecture.getSpeaker(), "강연자 정보는 null이면 안 됩니다.");
		assertEquals(VALID_SPEAKER.getId(), createdLecture.getSpeaker().getId());
	}
}
