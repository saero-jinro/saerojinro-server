package lecture;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.exception.LectureNotAuthorizedException;
import goorm.saerojinro.domain.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static goorm.saerojinro.common.domain.BaseRole.*;
import static goorm.saerojinro.domain.lecture.enums.LectureStatus.*;
import static org.junit.jupiter.api.Assertions.*;

class LectureDomainTest {

//	private static final User TEST_SPEAKER = User.builder()
//		.id(1L)
//		.name("Test Speaker")
//		.role(BaseRole.SPEAKER)
//		.build();

	private static final String TITLE = "Title";
	private static final String CONTENTS = "Contents";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "Location";
	private static final Category CATEGORY = Category.BACKEND;
	private static final LectureStatus STATUS = APPROVED;

	private Lecture lecture;

	@BeforeEach
	void setUp() {
		lecture = Lecture.create(
			null,
			TITLE,
			CONTENTS,
			MAX_CAPACITY,
			START_TIME,
			END_TIME,
			LOCATION,
			CATEGORY
		);
	}

	@Test
	@DisplayName("Lecture를 성공적으로 생성한다")
	void createLecture_success() {
		assertNotNull(lecture, "Lecture 객체가 null이면 안 됩니다.");
		assertEquals(TITLE, lecture.getTitle());
		assertEquals(CONTENTS, lecture.getContents());
		assertEquals(MAX_CAPACITY, lecture.getMaxCapacity());
		assertEquals(START_TIME, lecture.getStartTime());
		assertEquals(END_TIME, lecture.getEndTime());
		assertEquals(LOCATION, lecture.getLocation());
		assertEquals(CATEGORY, lecture.getCategory());
		assertEquals(STATUS, lecture.getLectureStatus());
	}

	@Test
	@DisplayName("Lecture를 성공적으로 수정한다")
	void updateLecture_success() {
		// given
		String newTitle = "Updated Title";
		String newContents = "Updated Contents";

		// when
		lecture.update(newTitle, newContents, MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY);

		// then
		assertEquals(newTitle, lecture.getTitle());
		assertEquals(newContents, lecture.getContents());
	}

	@Test
	@DisplayName("Lecture를 성공적으로 삭제한다")
	void deleteLecture_success() {
		// given
		User admin = User.builder()
			.id(999L)
			.name("Admin")
			.role(ADMIN)
			.build();

		// when
		lecture.delete();

		//then
		assertEquals(DELETED, lecture.getLectureStatus());
	}
}
