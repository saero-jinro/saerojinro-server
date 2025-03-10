package goorm.saerojinro.infra.repository.impl;

import org.springframework.stereotype.Repository;

import goorm.saerojinro.domain.eventlog.domain.EventLog;
import goorm.saerojinro.domain.eventlog.domain.EventLogRepository;
import goorm.saerojinro.infra.repository.jpa.EventLogJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EventLogRepositoryImpl implements EventLogRepository {
	private final EventLogJpaRepository eventLogJpaRepository;

	@Override
	public EventLog save(EventLog eventLog) {
		return eventLogJpaRepository.save(eventLog);
	}
}
