package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.dashboard.domain.DashboardRepository;
import goorm.saerojinro.infra.repository.redis.RedisDashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository
@RequiredArgsConstructor
public class DashboardRepositoryImpl implements DashboardRepository {
	private final RedisDashboardRepository redisDashboardRepository;

	@Override
	public List<Dashboard> findAll() {
		Iterable<Dashboard> all = redisDashboardRepository.findAll();

		return StreamSupport
			.stream(all.spliterator(), false)
			.toList();
	}

	@Override
	public Dashboard save(Dashboard dashboard) {
		return redisDashboardRepository.save(dashboard);
	}
}
