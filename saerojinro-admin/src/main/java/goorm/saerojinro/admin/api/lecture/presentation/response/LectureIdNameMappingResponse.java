package goorm.saerojinro.admin.api.lecture.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureIdNameMappingResponse(
	@Schema(description = "강의 ID", example = "1", requiredMode = REQUIRED)
	Long id,
	@Schema(description = "강의명", example = "AI와 미래 업무: 생성형 AI가 바꾸는 기업 혁신", requiredMode = REQUIRED)
	String title
	) {
	public static LectureIdNameMappingResponse from(Lecture lecture) {
		return LectureIdNameMappingResponse.builder()
			.id(lecture.getId())
			.title(lecture.getTitle())
			.build();
	}
}
