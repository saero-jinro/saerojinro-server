package goorm.saerojinro.domain.dashboard.domain;

import java.util.List;

public interface DashboardRepository {
	List<Dashboard> findAll();

	Dashboard save(Dashboard dashboard);
}
