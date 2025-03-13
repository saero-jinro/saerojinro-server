package goorm.saerojinro.admin.api.dashboard.presentation.response;

import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.dashboard.dto.DashboardAggregation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record DashboardResponse(
	@Schema(description = "강의 상위 리스트",
		example = "[{"
			+ "\"lectureId\": 1, "
			+ "\"rank\": 1, "
			+ "\"title\": \"클린코드 그렇게 하는거 아닌데\", "
			+ "\"speaker\": \"마틴 파울러\", "
			+ "\"reservation\": \"50\", "
			+ "\"wishlist\": \"20\"}]",
		requiredMode = REQUIRED)
	List<LectureRankResponse> lectureHighRank,

	@Schema(description = "강의 하위 리스트",
		example = "[{"
			+ "\"lectureId\": 1, "
			+ "\"rank\": 1, "
			+ "\"title\": \"개발 그렇게 하는거 아닌데\", "
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
	public static DashboardResponse from(DashboardAggregation data) {
		return DashboardResponse.builder()
			.lectureHighRank(buildLectureRankResponse(data.top10Dashboards()))
			.lectureLowRank(buildLectureRankResponse(data.bottom10Dashboards()))
			.timeRank(buildTimeRankResponse(data.top10Times()))
			.build();
	}

	private static List<LectureRankResponse> buildLectureRankResponse(List<Dashboard> dashboards) {
		return dashboards.stream()
			.map(d -> LectureRankResponse.from(d, dashboards.indexOf(d) + 1))
			.toList();
	}

	private static List<TimeRankResponse> buildTimeRankResponse(List<LocalDateTime> times) {
		return times.stream()
			.map(t -> TimeRankResponse.from(t, times.indexOf(t) + 1))
			.toList();
	}
}
