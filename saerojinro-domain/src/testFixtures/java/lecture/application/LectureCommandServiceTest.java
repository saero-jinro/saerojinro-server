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

	private static final String NAME = "Cole palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 / CEO";
	private static final String INTRODUCTION = "안녕하세요 OO 기업 CEO OOO 입니다";
	private static final String FILMOGRAPHY = "AA 기업 - 백엔드 개발 담당";

	private static final File SPEAKER_IMAGE_FILE = File.create(
		"Speaker_Image",
		"uploads/speaker/123456.jpg",
		3000L,
		"jpg"
	);
	private static final File THUMBNAIL_FILE = File.create(
		"Thumbnail_LogicalName",
		"uploads/lecture/thumbnail/123456.jpg",
		5000L,
		"jpg"
	);
	private static final File MATERIAL_FILE = File.create(
		"Material_LogicalName",
		"uploads/lecture/materials/발표자료.pdf",
		10000L,
		"pdf"
	);

	private static final String TITLE = "Title";
	private static final String CONTENTS = "Contents";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "Location";
	private static final Category CATEGORY = Category.BACKEND;

	private static final Speaker SPEAKER = Speaker.create(
		NAME, EMAIL, POSITION, INTRODUCTION, FILMOGRAPHY, SPEAKER_IMAGE_FILE
	);

	@BeforeEach
	void setUp() {
		lectureRepository = new FakeLectureRepository();
		speakerRepository = new FakeSpeakerRepository();
		lectureQueryService = new LectureQueryService(lectureRepository);
		lectureCommandService = new LectureCommandService(lectureRepository);
		speakerRepository.save(SPEAKER);
	}

	@Test
	@DisplayName("정상적으로 강의를 생성한다")
	void createLecture_success() {
		// when
		Lecture createdLecture = lectureCommandService.create(
			SPEAKER,
			TITLE,
			CONTENTS,
			THUMBNAIL_FILE,
			MATERIAL_FILE,
			MAX_CAPACITY,
			START_TIME,
			END_TIME,
			LOCATION,
			CATEGORY
		);

		// then
		assertNotNull(createdLecture);
		assertEquals(TITLE, createdLecture.getTitle());
		assertEquals(CONTENTS, createdLecture.getContents());
		assertEquals(EMAIL, createdLecture.getSpeaker().getEmail());
		assertNotNull(createdLecture.getThumbnailFile());
		assertNotNull(createdLecture.getMaterialFile());
		assertEquals("uploads/lecture/thumbnail/123456.jpg", createdLecture.getThumbnailFile().getPhysicalPath());
		assertEquals("uploads/lecture/materials/발표자료.pdf", createdLecture.getMaterialFile().getPhysicalPath());
	}

	@Test
	@DisplayName("정상적으로 강의를 수정한다")
	void updateLecture_success() {
		// given
		Lecture createdLecture = lectureCommandService.create(
			SPEAKER,
			TITLE,
			CONTENTS,
			THUMBNAIL_FILE,
			MATERIAL_FILE,
			MAX_CAPACITY,
			START_TIME,
			END_TIME,
			LOCATION,
			CATEGORY
		);

		// when
		Lecture lectureToUpdate = lectureQueryService.getById(createdLecture.getId());
		lectureCommandService.update(
			lectureToUpdate.getId(),
			"updated title",
			"updated contents",
			MAX_CAPACITY,
			START_TIME,
			END_TIME,
			LOCATION,
			CATEGORY
		);

		// then
		Lecture updatedLecture = lectureQueryService.getById(createdLecture.getId());
		assertNotNull(updatedLecture);
		assertEquals("updated title", updatedLecture.getTitle());
		assertEquals("updated contents", updatedLecture.getContents());
		assertEquals(MAX_CAPACITY, updatedLecture.getMaxCapacity());
		assertEquals(START_TIME, updatedLecture.getStartTime());
		assertEquals(EMAIL, updatedLecture.getSpeaker().getEmail());
	}

	@Test
	@DisplayName("정상적으로 강의를 삭제한다")
	void deleteLecture_success() {
		// given
		Lecture createdLecture = lectureCommandService.create(
			SPEAKER,
			TITLE,
			CONTENTS,
			THUMBNAIL_FILE,
			MATERIAL_FILE,
			MAX_CAPACITY,
			START_TIME,
			END_TIME,
			LOCATION,
			CATEGORY
		);

		// when
		Lecture lectureToDelete = lectureQueryService.getById(createdLecture.getId());
		lectureCommandService.delete(lectureToDelete.getId());

		// then
		Lecture deletedLecture = lectureQueryService.getById(createdLecture.getId());
		assertNotNull(deletedLecture.getDeletedAt());
	}
}
