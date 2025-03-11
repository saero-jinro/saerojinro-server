package goorm.saerojinro.api.lecture.presentation.response;

import lombok.Builder;
import java.util.List;

@Builder
public record LectureListResponseByDate(
	List<LectureResponseByDate> lectures,
	long totalCount
) {
	public static LectureListResponseByDate from(List<LectureResponseByDate> lectures) {
		return LectureListResponseByDate.builder()
			.lectures(lectures)
			.totalCount(lectures != null ? lectures.size() : 0)
			.build();
	}
}
