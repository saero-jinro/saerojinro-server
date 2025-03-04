package goorm.saerojinro.api.lecture.api;

import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LectureFacade {
	private final LectureQueryService lectureService;

	public List<LectureResponse> getAllLecture() {
		return lectureService.getAllLecture().stream()
			.map(LectureResponse::from)
			.collect(Collectors.toList());
	}

	public LectureDetailResponse getByLectureId(long lectureId) {
		Lecture lecture = lectureService.getByLectureId(lectureId);
		return LectureDetailResponse.from(lecture);
	}
}
