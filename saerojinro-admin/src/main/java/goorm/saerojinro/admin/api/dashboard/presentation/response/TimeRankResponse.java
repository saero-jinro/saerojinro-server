package goorm.saerojinro.admin.api.dashboard.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record TimeRankResponse(
	@Schema(description = "순위", example = "1", requiredMode = REQUIRED)
	int rank,

	@Schema(description = "날짜", example = "3월 12일 수요일", requiredMode = REQUIRED)
	String day,

	@Schema(description = "시작시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	LocalDateTime startTime,

	@Schema(description = "예상 인원", example = "200", requiredMode = REQUIRED)
	int expectation
) {
	public static TimeRankResponse from(LocalDateTime startTime, int rank, int expectation) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일", Locale.KOREAN);

		return TimeRankResponse.builder()
			.rank(rank)
			.day(startTime.format(formatter))
			.startTime(startTime)
			.expectation(expectation)
			.build();
	}
}
