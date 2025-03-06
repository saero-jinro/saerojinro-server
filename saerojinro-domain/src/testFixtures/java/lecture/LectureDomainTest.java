package lecture;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.exception.LectureDeleteNotAuthorizedException;
import goorm.saerojinro.domain.lecture.exception.LectureUpdateNotAuthorizedException;
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

	private static final User TEST_SPEAKER = User.builder()
		.id(1L)
		.name("Test Speaker")
		.role(BaseRole.SPEAKER)
		.build();

	private static final String TITLE = "Title";
	private static final String CONTENTS = "Contents";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "Location";
	private static final Category CATEGORY = Category.BACKEND;
	private static final LectureStatus STATUS = PENDING_APPROVAL;

	private Lecture lecture;

	@BeforeEach
	void setUp() {
		lecture = Lecture.create(
			TEST_SPEAKER,
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
		lecture.update(TEST_SPEAKER, newTitle, newContents);

		// then
		assertEquals(newTitle, lecture.getTitle());
		assertEquals(newContents, lecture.getContents());
	}

	@Test
	@DisplayName("다른 강연자 or 운영자가 아닐 경우 강의 수정시 예외를 반환한다")
	void LectureUpdateNotAuthorizedException() {
		// given
		User wrongSpeaker = User.builder()
			.id(2L)
			.name("Wrong Speaker")
			.role(BaseRole.SPEAKER)
			.build();

		// when & then
		Assertions.assertThrows(LectureUpdateNotAuthorizedException.class, () -> {
			lecture.update(wrongSpeaker, "Wrong Speaker", "Wrong Speaker");
		});
	}

	@Test
	@DisplayName("운영자가 강의를 수정을 할 수 있다")
	void updateLectureByAdmin_success() {
		// given
		User admin = User.builder()
			.id(999L)
			.name("Admin")
			.role(ADMIN)
			.build();

		// when
		lecture.update(admin, "updated title", "updated contents");

		//then
		assertEquals("updated title", lecture.getTitle());
		assertEquals("updated contents", lecture.getContents());
	}

	@Test
	@DisplayName("운영자가 강의 삭제시 삭제 완료 상태로 변경된다")
	void deleteLectureByAdmin_success() {
		// given
		User admin = User.builder()
			.id(999L)
			.name("Admin")
			.role(ADMIN)
			.build();

		// when
		lecture.requestDelete(admin);

		//then
		assertEquals(DELETED, lecture.getLectureStatus());
	}

	@Test
	@DisplayName("강연자 본인이 강의 삭제시 삭제 대기 상태로 변경된다")
	void deleteLectureByLecture_success() {
		// given
		User rightSpeaker = User.builder()
			.id(1L)
			.name("Right Speaker")
			.role(SPEAKER)
			.build();

		// when
		lecture.requestDelete(rightSpeaker);

		//then
		assertEquals(PENDING_DELETION, lecture.getLectureStatus());
	}

	@Test
	@DisplayName("강연자 본인이 아닐시 예외가 발생한다")
	void LectureDeleteNotAuthorizedException() {
		// given
		User wrongSpeaker = User.builder()
			.id(2L)
			.name("Wrong Speaker")
			.role(SPEAKER)
			.build();

		// when & then
		Assertions.assertThrows(LectureDeleteNotAuthorizedException.class, (() -> lecture.requestDelete(wrongSpeaker)));
	}
}
