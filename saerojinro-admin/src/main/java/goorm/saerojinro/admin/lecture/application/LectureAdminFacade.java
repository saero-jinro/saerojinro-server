package goorm.saerojinro.admin.lecture.application;

import goorm.saerojinro.admin.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.lecture.presentation.response.LectureCreateResponse;
import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.application.SpeakerCommandService;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LectureAdminFacade {
	private final LectureCommandService lectureCommandService;
	private final SpeakerCommandService speakerCommandService;

	public LectureCreateResponse create(LectureCreateRequest request) {
		Speaker speaker = speakerCommandService.create(
			request.speakerName(),
			request.speakerEmail(),
			request.speakerPosition(),
			request.speakerIntroduction(),
			request.speakerFilmography(),
			request.speakerPhoto()
		);
		Lecture lecture = lectureCommandService.create(
			speaker,
			request.title(),
			request.contents(),
			request.maxCapacity(),
			request.startTime(),
			request.endTime(),
			request.location(),
			request.category()
		);

		return LectureCreateResponse.from(speaker, lecture);
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
