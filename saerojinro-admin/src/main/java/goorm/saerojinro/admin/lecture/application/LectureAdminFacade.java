package goorm.saerojinro.admin.lecture.application;

import goorm.saerojinro.admin.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.lecture.presentation.response.LectureCreateResponse;
import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureAdminFacade {
	private final LectureCommandService lectureCommandService;

	public LectureCreateResponse create(LectureCreateRequest request) {
		Lecture lecture = lectureCommandService.create(
			null,
			request.title(),
			request.contents(),
			request.maxCapacity(),
			request.startTime(),
			request.endTime(),
			request.location(),
			request.category()
		);
		return LectureCreateResponse.from(lecture);
	}

	public void update(Long lectureId, LectureUpdateRequest request) {
		lectureCommandService.update(
			lectureId,
			request.title(),
			request.contents(),
			request.maxCapacity(),
			request.startTime(),
			request.endTime(),
			request.location(),
			request.category()
		);
	}

	public void delete(Long lectureId) {
		lectureCommandService.delete(lectureId);
	}
}
