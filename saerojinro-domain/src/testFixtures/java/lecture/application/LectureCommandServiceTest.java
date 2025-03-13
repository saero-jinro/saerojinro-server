package lecture.application;

import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.common.domain.Category;
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
	private static final String IMAGE_URI = "uploads/speaker";

	private static final String TITLE = "Title";
	private static final String CONTENTS = "Contents";
	private static final String THUMBNAIL_URI = "uploads/lecture/thumbnail";
	private static final String MATERIAL_URI = "uploads/lecture/material";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "Location";
	private static final Category CATEGORY = Category.BACKEND;

	private static final Speaker SPEAKER = Speaker.create(
		NAME, EMAIL, POSITION, INTRODUCTION, FILMOGRAPHY, IMAGE_URI
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
			SPEAKER, TITLE, CONTENTS, THUMBNAIL_URI, MATERIAL_URI,
			MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// then
		assertNotNull(createdLecture);
		assertEquals(TITLE, createdLecture.getTitle());
		assertEquals(CONTENTS, createdLecture.getContents());
		assertEquals(EMAIL, createdLecture.getSpeaker().getEmail());
	}

	@Test
	@DisplayName("정상적으로 강의를 수정한다")
	void updateLecture_success() {
		//given
		Lecture createdLecture = lectureCommandService.create(
			SPEAKER, TITLE, CONTENTS, THUMBNAIL_URI, MATERIAL_URI,
			MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// when
		Lecture findLecture = lectureQueryService.getById(createdLecture.getId());
		lectureCommandService.update(
			findLecture.getId(), "updated title", "updated contents",
			MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// then
		assertNotNull(createdLecture);

		assertEquals(1L, createdLecture.getId());
		assertEquals("updated title", createdLecture.getTitle());
		assertEquals("updated contents", createdLecture.getContents());
		assertEquals(MAX_CAPACITY, createdLecture.getMaxCapacity());
		assertEquals(START_TIME, createdLecture.getStartTime());
		assertEquals(EMAIL, createdLecture.getSpeaker().getEmail());
	}

	@Test
	@DisplayName("정상적으로 강의를 삭제한다")
	void deleteLecture_success() {
		//given
		Lecture createdLecture = lectureCommandService.create(
			SPEAKER, TITLE, CONTENTS, THUMBNAIL_URI, MATERIAL_URI,
			MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		// when
		Lecture findLecture = lectureQueryService.getById(createdLecture.getId());
		lectureCommandService.delete(findLecture.getId());

		// then
		assertNotNull(findLecture);
	}
}
