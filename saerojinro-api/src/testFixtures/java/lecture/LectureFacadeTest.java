package lecture;

import goorm.saerojinro.api.lecture.api.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static goorm.saerojinro.common.domain.BaseRole.*;
import static goorm.saerojinro.common.domain.Category.*;
import static goorm.saerojinro.domain.lecture.enums.LectureStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LectureFacadeTest {

	@Mock
	private LectureQueryService lectureQueryService;

	@InjectMocks
	private LectureFacade lectureFacade;

	private Lecture lecture1;
	private Lecture lecture2;

	@BeforeEach
	void setUp() {
		User speaker = User.builder()
			.id(1L)
			.name("Dummy Speaker")
			.role(SPEAKER)
			.build();

		lecture1 = Lecture.builder()
			.id(101L)
			.speaker(speaker)
			.title("Lecture One")
			.contents("Contents One")
			.startTime(LocalDateTime.of(2025, 3, 1, 10, 0))
			.endTime(LocalDateTime.of(2025, 3, 1, 12, 0))
			.location("room A")
			.category(BACKEND)
			.lectureStatus(PENDING_APPROVAL)
			.build();

		lecture2 = Lecture.builder()
			.id(102L)
			.speaker(speaker)
			.title("Lecture Two")
			.contents("Contents Two")
			.startTime(LocalDateTime.of(2025, 3, 2, 10, 0))
			.endTime(LocalDateTime.of(2025, 3, 2, 12, 0))
			.location("room B")
			.category(BACKEND)
			.lectureStatus(PENDING_APPROVAL)
			.build();
	}

	@Test
	@DisplayName("모든 강의를 조회할 수 있다")
	void getAllLecture_success() {
		// given
		List<Lecture> lectureList = Arrays.asList(lecture1, lecture2);
		when(lectureQueryService.getAllLecture()).thenReturn(lectureList);

		// when
		LectureListResponse responses = lectureFacade.getAllLecture();

		// then
		assertNotNull(responses);
		assertEquals(2, responses.totalCount());
		assertEquals(2, responses.lectures().size());

		LectureResponse response = responses.lectures().get(0);
		assertEquals("Lecture One", response.title());
		assertNotNull(response.speaker());
	}

	@Test
	@DisplayName("강의 아이디로 강의 상세 정보를 조회할 수 있다")
	void getByLectureId_success() {
		// given
		when(lectureQueryService.getByLectureId(101L)).thenReturn(lecture1);

		// when
		LectureDetailResponse detailResponse = lectureFacade.getByLectureId(101L);

		// then
		assertNotNull(detailResponse);
		assertEquals("Lecture One", detailResponse.title());
		assertEquals("Contents One", detailResponse.contents());
	}
}
