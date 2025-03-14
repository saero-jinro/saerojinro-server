package goorm.saerojinro.domain.dashboard.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TimeRankDto(
	int rank,
	LocalDateTime startTime,
	int expectation
) {
	public static TimeRankDto of(int rank, LocalDateTime startTime, int expectation) {
		return TimeRankDto.builder()
			.rank(rank)
			.startTime(startTime)
			.expectation(expectation)
			.build();
	}
}
