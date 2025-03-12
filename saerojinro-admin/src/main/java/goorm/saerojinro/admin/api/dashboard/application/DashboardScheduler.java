package goorm.saerojinro.admin.api.dashboard.application;

import goorm.saerojinro.domain.dashboard.application.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardScheduler {
	private final DashboardService dashboardService;
}
