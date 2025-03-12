package goorm.saerojinro.admin.api.dashboard.application;

import goorm.saerojinro.admin.api.dashboard.presentation.response.DashboardResponse;
import goorm.saerojinro.domain.dashboard.application.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardFacade {
	private final DashboardService dashboardService;

	public DashboardResponse getDashboard() {
		return null;
	}
}
