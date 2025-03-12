package goorm.saerojinro.admin.api.dashboard.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

// todo swagger
@Builder
public record DashboardResponse(

	@Schema(description = "강의 상위 리스트",
		example = "[{"
			+ "\"lectureId\": 1, "
			+ "\"rank\": 1, "
			+ "\"title\": \"클린코드란\", "
			+ "\"speaker\": \"마틴 파울러\", "
			+ "\"reservation\": \"50\", "
			+ "\"wishlist\": \"20\"}]",
		requiredMode = REQUIRED)
	List<LectureRankResponse> lectureHighRank,

	@Schema(description = "강의 하위 리스트",
		example = "[{"
			+ "\"lectureId\": 1, "
			+ "\"rank\": 1, "
			+ "\"title\": \"클린코드란\", "
			+ "\"speaker\": \"마틴 파울러\", "
			+ "\"reservation\": \"50\", "
			+ "\"wishlist\": \"20\"}]",
		requiredMode = REQUIRED)
	List<LectureRankResponse> lectureLowRank,

	@Schema(description = "강의 상위 리스트",
		example = "[{"
			+ "\"rank\": 1, "
			+ "\"day\": \"클린코드란\", "
			+ "\"startTime\": \"2025-03-01T10:00:00\"}]",
		requiredMode = REQUIRED)
	List<TimeRankResponse> timeRank
) {
	public static DashboardResponse of(
		List<LectureRankResponse> lectureHighRank,
		List<LectureRankResponse> lectureLowRank,
		List<TimeRankResponse> timeRank
	) {
		return DashboardResponse.builder()
			.lectureHighRank(lectureHighRank)
			.lectureLowRank(lectureLowRank)
			.timeRank(timeRank)
			.build();
	}
}
