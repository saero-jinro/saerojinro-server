package goorm.saerojinro.api.lecture.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureListResponse(
	@Schema(description = "강의 목록", requiredMode = REQUIRED)
	List<LectureResponse> lectures,

	@Schema(description = "총 강의 개수", example = "2")
	long totalCount
) {
	public static LectureListResponse from(List<LectureResponse> lectures) {
		return LectureListResponse.builder()
			.lectures(lectures)
			.totalCount(lectures != null ? lectures.size() : 0)
			.build();
	}
}
