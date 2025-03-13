package goorm.saerojinro.api.lecture.presentation.response;

import java.util.List;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.Builder;

@Builder
public record LectureSummaryListResponse(
	List<LectureSummaryResponse> responses,
	int total
) {
	public static LectureSummaryListResponse from(List<Lecture> responses) {
		return LectureSummaryListResponse.builder()
			.responses(
				responses.stream()
				.map(LectureSummaryResponse::from)
				.toList()
			)
			.total(responses.size())
			.build();
	}
}
