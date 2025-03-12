package goorm.saerojinro.domain.dashboard.application;

import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.dashboard.domain.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
	private final DashboardRepository dashboardRepository;

	public List<Dashboard> findAll() {
		return dashboardRepository.findAll();
	}

	public Dashboard save(Dashboard dashboard) {
		return dashboardRepository.save(dashboard);
	}
}
