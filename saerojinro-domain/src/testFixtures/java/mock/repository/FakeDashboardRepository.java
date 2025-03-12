package mock.repository;

import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.dashboard.domain.DashboardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakeDashboardRepository implements DashboardRepository {
	private final Map<Long, Dashboard> data = new ConcurrentHashMap<>();

	@Override
	public List<Dashboard> findAll() {
		return new ArrayList<>(data.values());
	}

	@Override
	public Dashboard save(Dashboard dashboard) {
		data.put(dashboard.getId(), dashboard);
		return dashboard;
	}
}
