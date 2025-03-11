package goorm.saerojinro.infra.repository.impl;

import org.springframework.stereotype.Repository;

import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.LogEventRepository;
import goorm.saerojinro.infra.repository.jpa.EventLogJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LogEventRepositoryImpl implements LogEventRepository {
	private final EventLogJpaRepository eventLogJpaRepository;

	@Override
	public LogEvent save(LogEvent logEvent) {
		return eventLogJpaRepository.save(logEvent);
	}
}
