package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureResponseByDate(
	@Schema(description = "강의 고유 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "강의명", example = "공간지능 혁신을 통한 온오프라인 통합 경험의 미래", requiredMode = REQUIRED)
	String title,

	@Schema(description = "강의 카테고리", example = "BACKEND", requiredMode = REQUIRED)
	Category category,

	@Schema(description = "강의 시작 시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	LocalDateTime startTime,

	@Schema(description = "강의 종료 시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
	LocalDateTime endTime,

	@Schema(description = "강연자 이름", example = "Cole Palmer", requiredMode = REQUIRED)
	String speakerName,

	@Schema(description = "강연자 사진", example = "local/file_0000000.jpg", requiredMode = REQUIRED)
	String speakerImageUri
) {
	public static LectureResponseByDate from(Lecture lecture) {
		return LectureResponseByDate.builder()
			.id(lecture.getId())
			.title(lecture.getTitle())
			.category(lecture.getCategory())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.speakerName(lecture.getSpeaker().getName())
			.speakerImageUri(lecture.getSpeaker().getImageUri())
			.build();
	}
}
