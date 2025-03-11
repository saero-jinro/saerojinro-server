package goorm.saerojinro.api.lecture.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record LectureListResponseByDate(
	@Schema(description = "강의 목록", requiredMode = REQUIRED)
	List<LectureResponseByDate> lectures,

	@Schema(description = "총 강의 개수", example = "2")
	long totalCount
) {
	public static LectureListResponseByDate from(List<LectureResponseByDate> lectures) {
		return LectureListResponseByDate.builder()
			.lectures(lectures)
			.totalCount(lectures != null ? lectures.size() : 0)
			.build();
	}
}
