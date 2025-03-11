package goorm.saerojinro.api.lecture.presentation.response;

import java.util.List;
import lombok.Builder;

@Builder
public record LectureListResponseByAll(
	List<LectureResponseByAll> lectures,
	long totalCount
) {
	public static LectureListResponseByAll from(List<LectureResponseByAll> lectures) {
		return LectureListResponseByAll.builder()
			.lectures(lectures)
			.totalCount(lectures != null ? lectures.size() : 0)
			.build();
	}
}
