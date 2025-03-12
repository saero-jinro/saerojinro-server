package lecture;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static goorm.saerojinro.common.domain.BaseRole.*;
import static org.junit.jupiter.api.Assertions.*;

class LectureDomainTest {

	private static final String LOGICAL_NAME = "FileDomain";
	private static final String PHYSICAL_PATH = "http://example.com/test.jpg";
	private static final Long FILE_SIZE = 1024L;
	private static final String EXTENSION = ".java";

	private static final String NAME = "Cole palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 / CEO";
	private static final String INTRODUCTION = "안녕하세요 OO 기업 CEO OOO 입니다";
	private static final String FILMOGRAPHY = "AA 기업 - 백엔드 개발 담당";

	private static final String TITLE = "Title";
	private static final String CONTENTS = "Contents";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "Location";
	private static final Category CATEGORY = Category.BACKEND;

	private static final File file = File.builder()
		.logicalName(LOGICAL_NAME)
		.physicalPath(PHYSICAL_PATH)
		.fileSize(FILE_SIZE)
		.extension(EXTENSION)
		.build();

	private static final Speaker speaker = Speaker.builder()
		.name(NAME)
		.email(EMAIL)
		.position(POSITION)
		.introduction(INTRODUCTION)
		.filmography(FILMOGRAPHY)
		.file(file)
		.build();

	private Lecture lecture;

	@BeforeEach
	void setUp() {
		lecture = Lecture.create(
			speaker,
			TITLE,
			CONTENTS,
			file,
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
		assertNotNull(lecture);
		assertEquals(TITLE, lecture.getTitle());
		assertEquals(CONTENTS, lecture.getContents());
		assertEquals(file, lecture.getThumbnail());
		assertEquals(MAX_CAPACITY, lecture.getMaxCapacity());
		assertEquals(START_TIME, lecture.getStartTime());
		assertEquals(END_TIME, lecture.getEndTime());
		assertEquals(LOCATION, lecture.getLocation());
		assertEquals(CATEGORY, lecture.getCategory());
		assertEquals("google@mail.com", lecture.getSpeaker().getEmail());
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
		assertNotNull(lecture.getDeletedAt());
	}
}
