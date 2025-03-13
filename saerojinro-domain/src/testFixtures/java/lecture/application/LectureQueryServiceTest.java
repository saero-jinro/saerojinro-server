package lecture.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import mock.repository.FakeLectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class LectureQueryServiceTest {

	private LectureQueryService lectureQueryService;
	private FakeLectureRepository fakeLectureRepository;

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

	@BeforeEach
	void setUp() {
		fakeLectureRepository = new FakeLectureRepository();
		lectureQueryService = new LectureQueryService(fakeLectureRepository);

		Speaker speaker = Speaker.create(
			NAME,
			EMAIL,
			POSITION,
			INTRODUCTION,
			FILMOGRAPHY,
			SPEAKER_IMAGE_FILE
		);

		Lecture lecture1 = Lecture.create(
			speaker,
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

		fakeLectureRepository.save(lecture1);
	}

	@Test
	@DisplayName("저장된 모든 강의를 조회할 수 있다.")
	void getAllLecture_success() {
		// when
		List<Lecture> lectures = lectureQueryService.getAllLecture();

		// then
		assertNotNull(lectures);
		assertEquals(1, lectures.size());
	}

	@Test
	@DisplayName("강의 ID로 특정한 강의 상세정보를 조회할 수 있다.")
	void getByLectureId_success() {
		// when
		Lecture lecture = lectureQueryService.getById(1L);

		// then
		assertNotNull(lecture);
		assertEquals(TITLE, lecture.getTitle());
	}

	@Test
	@DisplayName("강의가 존재하지 않으면 상세정보 조회시 예외를 반환한다.")
	void testGetByLectureId_notFound() {
		// when & then
		assertThatThrownBy(() -> lectureQueryService.getById(999L))
			.isInstanceOf(LectureNotFoundException.class);
	}

	@Test
	@DisplayName("특정 일자의 강의를 조회할 수 있다.")
	void getByDate_success() {
		// when
		List<Lecture> lectures = lectureQueryService.getByDate(LocalDate.of(2025, 3, 1));

		// then
		assertNotNull(lectures);
		assertEquals(TITLE, lectures.get(0).getTitle());
	}

	@Test
	@DisplayName("시작 시간으로 강의를 조회한다")
	void getAllLectureByStartTime_Success() {
		// when
		List<Lecture> lectureList = lectureQueryService.getAllLectureByStartTime(START_TIME);

		// then
		assertNotNull(lectureList);
		assertEquals(1, lectureList.size());
		assertEquals(START_TIME, lectureList.get(0).getStartTime());
	}

	@Test
	@DisplayName("getAllLectureBetween은 주어진 시간 사이에 있는 강의를 조회한다")
	void getAllLectureBetween_Success() {
		// when
		List<Lecture> lectureList = lectureQueryService.getAllLectureBetween(START_TIME, END_TIME);

		// then
		assertNotNull(lectureList);
		assertEquals(1, lectureList.size());
		assertEquals(START_TIME, lectureList.get(0).getStartTime());
	}
}
