package goorm.saerojinro.admin.api.lecture.application;

import goorm.saerojinro.admin.api.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.response.LectureCreateResponse;
import goorm.saerojinro.admin.api.lecture.presentation.response.LectureIdNameMappingListResponse;
import goorm.saerojinro.admin.api.lecture.presentation.response.LectureIdNameMappingResponse;
import goorm.saerojinro.domain.file.application.FileCommandService;
import goorm.saerojinro.domain.file.application.FileQueryService;
import goorm.saerojinro.domain.file.application.FileStorageService;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.application.SpeakerCommandService;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureAdminFacade {
	private final LectureCommandService lectureCommandService;
	private final SpeakerCommandService speakerCommandService;
	private final FileQueryService fileQueryService;
	private final FileCommandService fileCommandService;
	private final FileStorageService fileStorageService;
	private final LectureQueryService lectureQueryService;

	@Transactional
	public LectureCreateResponse create(LectureCreateRequest request) {
		File tempSpeakerFile = fileQueryService.getFileById(request.speakerPhotoId());
		File tempThumbnailFile = fileQueryService.getFileById(request.thumbnailId());
		File tempMaterialFile = fileQueryService.getFileById(request.materialId());

		Speaker speaker = speakerCommandService.create(
			request.speakerName(),
			request.speakerEmail(),
			request.speakerPosition(),
			request.speakerIntroduction(),
			request.speakerFilmography(),
			tempSpeakerFile
		);

		Lecture lecture = lectureCommandService.create(
			speaker,
			request.title(),
			request.contents(),
			tempThumbnailFile,
			tempMaterialFile,
			request.maxCapacity(),
			request.startTime(),
			request.endTime(),
			request.location(),
			request.category()
		);

		String thumbnailPath = fileStorageService.moveFileDir(
			tempThumbnailFile.getPhysicalPath(), lecture.getId(), "thumbnail"
		);
		fileCommandService.updateFilePhysicalPath(tempThumbnailFile.getId(), thumbnailPath);

		String materialPath = fileStorageService.moveFileDir(
			tempMaterialFile.getPhysicalPath(), lecture.getId(), "materials"
		);
		fileCommandService.updateFilePhysicalPath(tempMaterialFile.getId(), materialPath);

		String speakerProfilePath = fileStorageService.moveFileDir(
			tempSpeakerFile.getPhysicalPath(), lecture.getId(), "speaker"
		);
		fileCommandService.updateFilePhysicalPath(tempSpeakerFile.getId(), speakerProfilePath);

		return LectureCreateResponse.from(lecture);
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

	@Transactional(readOnly = true)
	public LectureIdNameMappingListResponse findAll() {
		List<Lecture> lectures = lectureQueryService.getAll();

		List<LectureIdNameMappingResponse> responses = lectures
			.stream()
			.map(LectureIdNameMappingResponse::from)
			.toList();

		return LectureIdNameMappingListResponse.from(responses);
	}
}
