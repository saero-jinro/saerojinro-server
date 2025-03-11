package goorm.saerojinro.api.lecture.application;

import goorm.saerojinro.api.lecture.presentation.response.*;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureFacade {
	private final LectureQueryService lectureService;

	@Transactional(readOnly = true)
	public LectureListResponseByAll getAllLecture() {
		List<LectureResponseByAll> responses = lectureService.getAllLecture().stream()
			.map(LectureResponseByAll::from)
			.toList();
		return LectureListResponseByAll.from(responses);
	}

	@Transactional(readOnly = true)
	public LectureDetailResponse getByLectureId(long lectureId) {
		Lecture lecture = lectureService.getByLectureId(lectureId);
		return LectureDetailResponse.from(lecture);
	}

	@Transactional(readOnly = true)
	public LectureListResponseByDate getByDate(LocalDate localDate) {
		List<LectureResponseByDate> responses = lectureService.getByDate(localDate).stream()
			.map(LectureResponseByDate::from)
			.toList();
		return LectureListResponseByDate.from(responses);
	}
}
