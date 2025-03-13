package goorm.saerojinro.admin.api.lecture.application;

import goorm.saerojinro.admin.api.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.response.LectureCreateResponse;
import goorm.saerojinro.domain.file.application.FileQueryService;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.application.SpeakerCommandService;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LectureAdminFacade {
	private final LectureCommandService lectureCommandService;
	private final SpeakerCommandService speakerCommandService;
	private final FileQueryService fileQueryService;

	@Transactional
	public LectureCreateResponse create(LectureCreateRequest request) {
		File speakerImageFile = fileQueryService.getFileByUri(request.speakerPhotoUri());

		Speaker speaker = speakerCommandService.create(
			request.speakerName(),
			request.speakerEmail(),
			request.speakerPosition(),
			request.speakerIntroduction(),
			request.speakerFilmography(),
			speakerImageFile
		);

		File thumbnailFile = fileQueryService.getFileByUri(request.thumbnailUri());
		File materialFile = fileQueryService.getFileByUri(request.materialsUri());

		fileQueryService.getFileByUri(request.speakerPhotoUri());
		Lecture lecture = lectureCommandService.create(
			speaker,
			request.title(),
			request.contents(),
			thumbnailFile,
			materialFile,
			request.maxCapacity(),
			request.startTime(),
			request.endTime(),
			request.location(),
			request.category()
		);

		return LectureCreateResponse.from(speaker, lecture);
	}

	@Transactional
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

	@Transactional
	public void delete(Long lectureId) {
		lectureCommandService.delete(lectureId);
	}
}
