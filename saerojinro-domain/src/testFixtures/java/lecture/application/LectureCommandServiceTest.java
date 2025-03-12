package lecture.application;

import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeSpeakerRepository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LectureCommandServiceTest {

	private LectureCommandService lectureCommandService;
	private LectureQueryService lectureQueryService;
	private FakeLectureRepository lectureRepository;
	private FakeSpeakerRepository speakerRepository;

	private static final String TITLE = "Lecture Title";
	private static final String CONTENTS = "Lecture Contents";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "room A";
	private static final Category CATEGORY = Category.BACKEND;

	private static final String NAME = "Cole palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 / CEO";
	private static final String INTRODUCTION = "안녕하세요 OO 기업 CEO OOO 입니다";
	private static final String FILMOGRAPHY = "AA 기업 - 백엔드 개발 담당";

	private static final String LOGICAL_NAME = "FileDomain";
	private static final String PHYSICAL_PATH = "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/sk/2024/03/18/SK007_20240318_261101.jpg";
	private static final Long FILE_SIZE = 1024L;
	private static final String EXTENSION = ".java";

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

	@BeforeEach
	void setUp() {
		lectureRepository = new FakeLectureRepository();
		speakerRepository = new FakeSpeakerRepository();
		lectureQueryService = new LectureQueryService(lectureRepository);
		lectureCommandService = new LectureCommandService(lectureRepository);
		speakerRepository.save(speaker);
	}

	@Test
	@DisplayName("정상적으로 강의를 생성한다")
	void createLecture_success() {
		// when
		Lecture createdLecture = lectureCommandService.create(
			speaker, TITLE, CONTENTS, file, MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
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

		assertEquals("google@mail.com", createdLecture.getSpeaker().getEmail());
	}

	@Test
	@DisplayName("정상적으로 강의를 수정한다")
	void updateLecture_success() {
		//given
		Lecture createdLecture = lectureCommandService.create(
			speaker, TITLE, CONTENTS, file,MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// when
		Lecture findLecture = lectureQueryService.getByLectureId(createdLecture.getId());
		lectureCommandService.update(
			findLecture.getId(), "updated title", "updated contents",
			MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY);

		// then
		assertNotNull(createdLecture);

		assertEquals(1L, createdLecture.getId());
		assertEquals("updated title", createdLecture.getTitle());
		assertEquals("updated contents", createdLecture.getContents());
		assertEquals(MAX_CAPACITY, createdLecture.getMaxCapacity());
		assertEquals(START_TIME, createdLecture.getStartTime());
		assertEquals(END_TIME, createdLecture.getEndTime());
		assertEquals(LOCATION, createdLecture.getLocation());
		assertEquals(CATEGORY, createdLecture.getCategory());

		assertEquals("google@mail.com", createdLecture.getSpeaker().getEmail());
	}

	@Test
	@DisplayName("정상적으로 강의를 삭제한다")
	void deleteLecture_success() {
		//given
		Lecture createdLecture = lectureCommandService.create(
			null, TITLE, CONTENTS, file, MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// when
		Lecture findLecture = lectureQueryService.getByLectureId(createdLecture.getId());
		lectureCommandService.delete(findLecture.getId());

		// then
		assertNotNull(findLecture);
	}
}
