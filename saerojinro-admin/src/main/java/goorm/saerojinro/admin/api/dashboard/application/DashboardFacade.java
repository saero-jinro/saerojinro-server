package goorm.saerojinro.admin.api.dashboard.application;

import goorm.saerojinro.admin.api.dashboard.presentation.response.DashboardResponse;
import goorm.saerojinro.domain.dashboard.application.DashboardService;
import goorm.saerojinro.domain.dashboard.dto.DashboardAggregation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardFacade {
	private final DashboardService dashboardService;

	public DashboardResponse getDashboard() {
		DashboardAggregation all = dashboardService.findAll();
		return DashboardResponse.from(all);
	}
}
