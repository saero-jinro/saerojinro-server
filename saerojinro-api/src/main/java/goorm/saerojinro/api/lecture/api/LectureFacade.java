package goorm.saerojinro.api.lecture.api;

import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureFacade {
	private final LectureQueryService lectureService;

	public LectureListResponse getAllLecture() {
		List<LectureResponse> responses = lectureService.getAllLecture().stream()
			.map(LectureResponse::from)
			.toList();
		return LectureListResponse.from(responses);
	}

	public LectureDetailResponse getByLectureId(long lectureId) {
		Lecture lecture = lectureService.getByLectureId(lectureId);
		return LectureDetailResponse.from(lecture);
	}
}
