package goorm.saerojinro.api.lecture.presentation.response;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureListResponseByAll(
	@Schema(description = "강의 목록", requiredMode = REQUIRED)
	List<LectureResponseByAll> lectures,

	@Schema(description = "총 강의 개수", example = "10")
	long totalCount
) {
	public static LectureListResponseByAll from(List<LectureResponseByAll> lectures) {
		return LectureListResponseByAll.builder()
			.lectures(lectures)
			.totalCount(lectures != null ? lectures.size() : 0)
			.build();
	}
}
