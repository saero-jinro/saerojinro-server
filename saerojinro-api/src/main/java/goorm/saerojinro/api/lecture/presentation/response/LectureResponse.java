package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.dto.LectureCacheDTO;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureResponse(
	@Schema(description = "강의 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "강의 썸네일 Uri", example = "bucketUrl/uploads/{lectureId}/thumbnail/123456789.jpg", requiredMode = REQUIRED)
	String thumbnailUri,

	@Schema(description = "강의 시작 시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	String startTime,

	@Schema(description = "강의 종료 시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
	String endTime,

	@Schema(description = "강의명", example = "AI와 미래 업무: 생성형 AI가 바꾸는 기업 혁신", requiredMode = REQUIRED)
	String title,

	@Schema(description = "강의실 위치", example = "ROOM A-1", requiredMode = REQUIRED)
	String location,

	@Schema(description = "강연자 이름", example = "박민수", requiredMode = REQUIRED)
	String speakerName,

	@Schema(description = "카테고리", example = "BACKEND", requiredMode = REQUIRED)
	Category category
) {
	public static LectureResponse from(LectureCacheDTO lectureCacheDTO) {
		return LectureResponse.builder()
			.id(lectureCacheDTO.id())
			.title(lectureCacheDTO.title())
			.category(lectureCacheDTO.category())
			.startTime(lectureCacheDTO.startTime())
			.endTime(lectureCacheDTO.endTime())
			.speakerName(lectureCacheDTO.speakerName())
			.speakerImageUri(lectureCacheDTO.speakerImageUri())
			.thumbnailUri(lecture.getThumbnailFile().getPhysicalPath())
			.location(lecture.getLocation())
			.build();
	}
}
