package goorm.saerojinro.infra.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import goorm.saerojinro.domain.logevent.domain.LogEvent;

public interface EventLogJpaRepository extends JpaRepository<LogEvent, Long> {
}
