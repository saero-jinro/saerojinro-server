package goorm.saerojinro.api.lecture.presentation.response;

import java.util.List;
import lombok.Builder;

@Builder
public record LectureListResponse(
	List<LectureResponse> lectures,
	long totalCount
) {
	public static LectureListResponse from(List<LectureResponse> lectures) {
		return LectureListResponse.builder()
			.lectures(lectures)
			.totalCount(lectures != null ? lectures.size() : 0)
			.build();
	}
}
