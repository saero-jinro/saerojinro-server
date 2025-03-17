package lecture.application;

import static goorm.saerojinro.common.domain.Category.BACKEND;
import static goorm.saerojinro.common.domain.Category.FRONTEND;
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
import java.util.Map;

public class LectureQueryServiceTest {
	private LectureQueryService lectureQueryService;

	private Lecture lecture1;
	private Lecture lecture2;

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
	private static final Category CATEGORY = BACKEND;

	@BeforeEach
	void setUp() {
		FakeLectureRepository fakeLectureRepository = new FakeLectureRepository();
		lectureQueryService = new LectureQueryService(fakeLectureRepository);

		Speaker speaker = Speaker.builder()
			.id(1L)
			.name(NAME)
			.email(EMAIL)
			.build();

		lecture1 = fakeLectureRepository.save(Lecture.create(
			speaker, TITLE, CONTENTS, THUMBNAIL_FILE, MATERIAL_FILE, MAX_CAPACITY,
				START_TIME, END_TIME, LOCATION, CATEGORY
			)
		);

		lecture2 = fakeLectureRepository.save(Lecture.create(
			speaker, TITLE + 2, CONTENTS, THUMBNAIL_FILE, MATERIAL_FILE, MAX_CAPACITY,
				START_TIME, END_TIME, LOCATION, CATEGORY
			)
		);
	}

	@Test
	@DisplayName("저장된 모든 강의를 조회할 수 있다.")
	void getAllLecture_success() {
		// when
		List<Lecture> lectures = lectureQueryService.getAll();

		// then
		assertEquals(2, lectures.size());
		assertEquals(TITLE, lectures.get(0).getTitle());
		assertEquals(TITLE + 2, lectures.get(1).getTitle());
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
	@DisplayName("강의 ID로 특정한 강의 상세정보를 조회할 수 있다.")
	void getByLectureIdWithLock_success() {
		// when
		Lecture lecture = lectureQueryService.getByIdWithLock(1L);

		// then
		assertNotNull(lecture, "강의 객체는 null이면 안 됩니다.");
		assertEquals(TITLE, lecture.getTitle(), "강의 제목이 일치해야 합니다.");
	}

	@Test
	@DisplayName("강의가 존재하지 않으면 상세정보 조회시 예외를 반환한다.")
	void testGetByLectureId_notFound() {
		// when & then
		assertThatThrownBy(() -> lectureQueryService.getById(999L))
			.isInstanceOf(LectureNotFoundException.class);
	}

	@Test
	@DisplayName("강의가 존재하지 않으면 상세정보 조회시 예외를 반환한다.")
	void testGetByLectureIdWithLock_notFound() {
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
		assertEquals(TITLE + 2, lectures.get(1).getTitle());
	}

	@Test

	@DisplayName("getByStartTime은 시작 시간으로 강의를 조회한다")
	void getByStartTime_Success() {
		// when
		List<Lecture> lectureList = lectureQueryService.getByStartTime(
			LocalDateTime.of(2025, 3, 1, 10, 0));

		// then
		assertNotNull(lectureList);
		assertEquals(2, lectureList.size());
		assertEquals(START_TIME, lectureList.get(0).getStartTime());
	}

	@Test
	@DisplayName("getAllLectureBetween은 주어진 시간 사이에 있는 강의를 조회한다")
	void getAllLectureBetween_Success() {
		// when
		List<Lecture> lectureList = lectureQueryService.getAllLectureBetween(
			LocalDateTime.of(2025, 3, 1, 10, 0),
			LocalDateTime.of(2025, 3, 1, 12, 0));

		// then
		assertNotNull(lectureList);
		assertEquals(2, lectureList.size());
		assertEquals(START_TIME, lectureList.get(0).getStartTime());
	}

	@Test
	@DisplayName("getRecommendedLectureByDate는 해당 시간에 맞는 강의 중 유저 활동 기반 강의 리스트를 조회한다.")
	void getRecommendedLectureByDate_Success() {
		// given
		Map<Category, Integer> categoryPriorityMap = Map.of(BACKEND, 1, FRONTEND, 2);
		LocalDateTime startTime = LocalDateTime.of(2025, 3, 1, 10, 0);

		// when
		List<Lecture> response = lectureQueryService.getRecommendedLectureByDate(categoryPriorityMap, startTime);

		// then
		assertEquals(lecture1.getTitle(), response.get(0).getTitle());
		assertEquals(lecture2.getTitle(), response.get(1).getTitle());
	}
}
