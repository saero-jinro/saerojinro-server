package goorm.saerojinro.infra.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import goorm.saerojinro.domain.logevent.domain.LogEvent;

public interface EventLogJpaRepository extends JpaRepository<LogEvent, Long> {
	@Query("SELECT l FROM LogEvent l WHERE l.user.id = :userId ORDER BY l.timestamp DESC LIMIT 50")
	List<LogEvent> findRecentLogByUserId(Long userId);
}
