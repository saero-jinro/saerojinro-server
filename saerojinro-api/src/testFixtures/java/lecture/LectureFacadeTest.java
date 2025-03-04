package lecture;

import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeLectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static goorm.saerojinro.common.domain.BaseRole.SPEAKER;
import static goorm.saerojinro.common.domain.Category.BACKEND;
import static goorm.saerojinro.domain.lecture.enums.LectureStatus.PENDING_APPROVAL;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LectureFacadeTest {

	private LectureQueryService lectureQueryService;
	private FakeLectureRepository lectureRepository;

	private Lecture lecture1;
	private Lecture lecture2;

	@BeforeEach
	void setUp() {
		lectureRepository = new FakeLectureRepository();
		lectureQueryService = new LectureQueryService(lectureRepository);

		User speaker = User.builder()
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
			.startTime(LocalDateTime.of(2025, 3, 1, 10, 0))
			.endTime(LocalDateTime.of(2025, 3, 1, 12, 0))
			.location("room B")
			.category(BACKEND)
			.lectureStatus(PENDING_APPROVAL)
			.build();

		lectureRepository.save(lecture1);
		lectureRepository.save(lecture2);
	}

	@Test
	@DisplayName("모든 강의를 조회할 수 있다")
	void getAllLecture_success() {
		List<Lecture> lectures = lectureQueryService.getAllLecture();
		assertNotNull(lectures);
		assertEquals(2, lectures.size());
	}

	@Test
	@DisplayName("강의 아이디로 강의 상세 정보를 조회할 수 있다")
	void getByLectureId_success() {
		Lecture lecture = lectureQueryService.getByLectureId(1L);
		assertNotNull(lecture);
		assertEquals("Lecture One", lecture.getTitle());
	}

	@Test
	@DisplayName("존재하지 않는 강의 ID면 예외를 반환한다")
	void getByLectureId_notFound() {
		assertThrows(LectureNotFoundException.class, () -> lectureQueryService.getByLectureId(999L));
	}

	@Test
	@DisplayName("주어진 날짜에 해당하는 강의를 조회할 수 있다")
	void getByDate_success() {
		LocalDate date = LocalDate.of(2025, 3, 1);
		List<Lecture> lecturesOnDay = lectureQueryService.getByDate(date);
		assertNotNull(lecturesOnDay);
		assertEquals(2, lecturesOnDay.size());
		assertEquals("Lecture Two", lecturesOnDay.get(1).getTitle());
	}
}
