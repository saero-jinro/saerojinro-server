package lecture.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import goorm.saerojinro.common.domain.Category;
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
			.photo("Photo uri")
			.build();

		Lecture lecture1 = Lecture.create(
			SPEAKER,
			"Lecture One",
			"Content One",
			100L,
			LocalDateTime.of(2025, 3, 1, 10, 0),
			LocalDateTime.of(2025, 3, 1, 12, 0),
			"Location One",
			Category.BACKEND
		);

		Lecture lecture2 = Lecture.create(
			SPEAKER,
			"Lecture Two",
			"Content Two",
			100L,
			LocalDateTime.of(2025, 3, 1, 10, 0),
			LocalDateTime.of(2025, 3, 1, 12, 0),
			"Location Two",
			Category.BACKEND
		);

		fakeLectureRepository.save(lecture1);
		fakeLectureRepository.save(lecture2);
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
		Lecture lecture = lectureQueryService.getByLectureId(1L);

		// then
		assertNotNull(lecture, "강의 객체는 null이면 안 됩니다.");
		assertEquals("Lecture One", lecture.getTitle(), "강의 제목이 일치해야 합니다.");
	}

	@Test
	@DisplayName("강의가 존재하지 않으면 상세정보 조회시 예외를 반환한다.")
	void testGetByLectureId_notFound() {
		// when & then
		assertThatThrownBy(() -> lectureQueryService.getByLectureId(999L))
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
}
