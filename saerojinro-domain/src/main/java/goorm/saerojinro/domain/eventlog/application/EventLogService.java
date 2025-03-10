package goorm.saerojinro.domain.eventlog.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.domain.eventlog.domain.EventLog;
import goorm.saerojinro.domain.eventlog.domain.EventLogRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventLogService {
	private final EventLogRepository eventLogRepository;

	@Transactional
	public void save(EventLog eventLog) {
		eventLogRepository.save(eventLog);
	}
}
