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
	private FakeLectureRepository fakeLectureRepository;

	private Lecture lecture1;
	private Lecture lecture2;

	private static final String LOGICAL_NAME = "FileDomain";
	private static final String PHYSICAL_PATH = "http://example.com/test.jpg";
	private static final Long FILE_SIZE = 1024L;
	private static final String EXTENSION = ".java";

	private static final File file = File.builder()
		.logicalName(LOGICAL_NAME)
		.physicalPath(PHYSICAL_PATH)
		.fileSize(FILE_SIZE)
		.extension(EXTENSION)
		.build();

	@BeforeEach
	void setUp() {
		fakeLectureRepository = new FakeLectureRepository();
		lectureQueryService = new LectureQueryService(fakeLectureRepository);

		final Speaker SPEAKER = Speaker.builder()
			.name("Cole palmer")
			.email("google@mail.com")
			.position("00 기업 CEO")
			.introduction("안녕하세요 반가워용")
			.filmography("AA 기업  - 백엔드 개발")
			.file(file)
			.build();

		lecture1 = fakeLectureRepository.save(Lecture.create(
			SPEAKER,
			"Lecture One",
			"Content One",
			file,
			100L,
			LocalDateTime.of(2025, 3, 1, 10, 0),
			LocalDateTime.of(2025, 3, 1, 12, 0),
			"Location One",
			BACKEND)
		);

		lecture2 = fakeLectureRepository.save(Lecture.create(
			SPEAKER,
			"Lecture Two",
			"Content Two",
			file,
			100L,
			LocalDateTime.of(2025, 3, 1, 10, 0),
			LocalDateTime.of(2025, 3, 1, 12, 0),
			"Location Two",
			FRONTEND)
		);
	}

	@Test
	@DisplayName("저장된 모든 강의를 조회할 수 있다.")
	void getAllLecture_success() {
		// when
		List<Lecture> lectures = lectureQueryService.getAllLecture();

		// then
		assertNotNull(lectures, "강의 목록은 null이면 안 됩니다.");
		assertEquals(2, lectures.size(), "저장된 강의 수는 2여야 합니다.");
	}

	@Test
	@DisplayName("강의 ID로 특정한 강의 상세정보를 조회할 수 있다.")
	void getByLectureId_success() {
		// when
		Lecture lecture = lectureQueryService.getById(1L);

		// then
		assertNotNull(lecture, "강의 객체는 null이면 안 됩니다.");
		assertEquals("Lecture One", lecture.getTitle(), "강의 제목이 일치해야 합니다.");
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
		assertEquals("Lecture One", lectures.get(0).getTitle());
		assertEquals("Lecture Two", lectures.get(1).getTitle());
	}

	@Test
	@DisplayName("getAllLectureByStartTime은 시작 시간으로 강의를 조회한다")
	void getAllLectureByStartTime_Success() {
		// when
		List<Lecture> lectureList = lectureQueryService.getAllLectureByStartTime(
			LocalDateTime.of(2025, 3, 1, 10, 0));

		// then
		assertNotNull(lectureList);
		assertEquals(2, lectureList.size());
		assertEquals(
			LocalDateTime.of(2025, 3, 1, 10, 0),
			lectureList.get(0).getStartTime());
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
		assertEquals(LocalDateTime.of(2025, 3, 1, 10, 0),
			lectureList.get(0).getStartTime());
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
