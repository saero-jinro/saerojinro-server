package goorm.saerojinro.admin.api.lecture.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record LectureCreateResponse(
	@Schema(description = "강의 ID", example = "1", requiredMode = REQUIRED)
	Long id
) {
	public static LectureCreateResponse from(Lecture lecture) {
		return LectureCreateResponse.builder()
			.id(lecture.getId())
			.build();
	}
}
