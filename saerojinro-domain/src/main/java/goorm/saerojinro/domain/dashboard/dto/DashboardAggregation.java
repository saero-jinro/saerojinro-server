package goorm.saerojinro.domain.dashboard.dto;

import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DashboardAggregation(
	List<Dashboard> top10Dashboards,
	List<Dashboard> bottom10Dashboards,
	List<LocalDateTime> top10Times
) {
	public static DashboardAggregation of(
		List<Dashboard> top10Dashboards, List<Dashboard> bottom10Dashboards, List<LocalDateTime> top10Times) {
		return DashboardAggregation.builder()
			.top10Dashboards(top10Dashboards)
			.bottom10Dashboards(bottom10Dashboards)
			.top10Times(top10Times)
			.build();
	}
}
