package goorm.saerojinro.infra.repository.redis;

import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import org.springframework.data.repository.CrudRepository;

public interface RedisDashboardRepository extends CrudRepository<Dashboard, Long> {
}
