package lecture;

import goorm.saerojinro.common.model.Category;
import goorm.saerojinro.common.model.LectureStatus;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LectureDomainTest {

	private static final String TITLE = "Java Basics";
	private static final String CONTENTS = "Introduction to Java Language";
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "Room 101";
	private static final Category CATEGORY = Category.BACKEND;
	private static final LectureStatus STATUS = LectureStatus.PENDING_APPROVAL;

	private Lecture lecture;

	@BeforeEach
	void setUp() {
		lecture = Lecture.createLecture(
			TITLE,
			CONTENTS,
			START_TIME,
			END_TIME,
			LOCATION,
			CATEGORY,
			STATUS
		);
	}

	@Test
	@DisplayName("Lecture를 성공적으로 생성한다")
	void createLecture_success() {
		assertNotNull(lecture, "Lecture 객체가 null이면 안 됩니다.");
		assertEquals(TITLE, lecture.getTitle());
		assertEquals(CONTENTS, lecture.getContents());
		assertEquals(START_TIME, lecture.getStartTime());
		assertEquals(END_TIME, lecture.getEndTime());
		assertEquals(LOCATION, lecture.getLocation());
		assertEquals(CATEGORY, lecture.getCategory());
		assertEquals(STATUS, lecture.getLectureStatus());
	}
}
