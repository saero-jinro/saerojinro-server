package goorm.saerojinro.infra.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import goorm.saerojinro.domain.eventlog.domain.EventLog;

public interface EventLogJpaRepository extends JpaRepository<EventLog, Long> {
}
