package goorm.saerojinro.admin.api.lecture.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureIdNameMappingListResponse(
	@Schema(description = "강의 목록", requiredMode = REQUIRED)
	List<LectureIdNameMappingResponse> lectures,

	@Schema(description = "총 강의 개수", example = "2")
	long totalCount
) {
	public static LectureIdNameMappingListResponse from(List<LectureIdNameMappingResponse> lectures) {
		return LectureIdNameMappingListResponse.builder()
			.lectures(lectures)
			.totalCount(lectures.size())
			.build();
	}
}
