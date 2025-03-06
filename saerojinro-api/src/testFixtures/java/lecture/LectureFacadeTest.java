package lecture;

import static goorm.saerojinro.common.domain.BaseRole.*;
import static goorm.saerojinro.common.domain.Category.*;
import static goorm.saerojinro.domain.lecture.enums.LectureStatus.*;
import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.api.lecture.application.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeLectureRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LectureFacadeTest {

	private LectureFacade lectureFacade;
	private LectureQueryService lectureQueryService;
	private FakeLectureRepository lectureRepository;

	private Lecture lecture1;
	private Lecture lecture2;

	@BeforeEach
	void setUp() {
		lectureRepository = new FakeLectureRepository();
		lectureQueryService = new LectureQueryService(lectureRepository);

		lectureFacade = new LectureFacade(lectureQueryService);

		User speaker = User.builder()
			.id(1L)
			.name("Speaker")
			.role(SPEAKER)
			.build();

		lecture1 = Lecture.builder()
			.speaker(speaker)
			.title("Lecture One")
			.contents("Contents One")
			.maxCapacity(100L)
			.startTime(LocalDateTime.of(2025, 3, 1, 10, 0))
			.endTime(LocalDateTime.of(2025, 3, 1, 12, 0))
			.location("room A")
			.category(BACKEND)
			.lectureStatus(PENDING_APPROVAL)
			.build();

		lecture2 = Lecture.builder()
			.speaker(speaker)
			.title("Lecture Two")
			.contents("Contents Two")
			.maxCapacity(100L)
			.startTime(LocalDateTime.of(2025, 3, 1, 14, 0))
			.endTime(LocalDateTime.of(2025, 3, 1, 16, 0))
			.location("room B")
			.category(BACKEND)
			.lectureStatus(PENDING_APPROVAL)
			.build();

		lectureRepository.save(lecture1);
		lectureRepository.save(lecture2);
	}

	@Test
	@DisplayName("전체 강의 목록을 리스트로 조회할 수 있다")
	void getAllLecture_success() {
		// when
		LectureListResponse response = lectureFacade.getAllLecture();

		// then
		assertNotNull(response);
		assertEquals(2, response.totalCount());
		assertEquals(2, response.lectures().size());

		assertEquals("Lecture One", response.lectures().get(0).title());
		assertEquals("Speaker", response.lectures().get(0).speakerName());
	}

	@Test
	@DisplayName("강의 아이디로 강의 상세 정보를 조회할 수 있다")
	void getByLectureId_success() {
		LectureDetailResponse detail = lectureFacade.getByLectureId(1L);

		assertNotNull(detail);
		assertEquals("Lecture One", detail.title());
		assertEquals("Contents One", detail.contents());
	}

	@Test
	@DisplayName("존재하지 않는 강의 ID면 예외를 반환한다")
	void getByLectureId_notFound() {
		assertThrows(LectureNotFoundException.class, () -> lectureQueryService.getByLectureId(999L));
	}

	@Test
	@DisplayName("주어진 날짜에 해당하는 강의를 조회할 수 있다")
	void getByDate_success() {
		// given: lecture1, lecture2 모두 2025-03-01에 시작
		LocalDate date = LocalDate.of(2025, 3, 1);

		// when
		LectureListResponse response = lectureFacade.getByDate(date);

		// then
		assertNotNull(response);
		assertEquals(2, response.lectures().size());
		assertEquals("Lecture One", response.lectures().get(0).title());
		assertEquals("Lecture Two", response.lectures().get(1).title());
	}
}
