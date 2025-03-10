package goorm.saerojinro.domain.eventlog.domain;

import goorm.saerojinro.domain.eventlog.domain.dto.EventLogDTO;

public interface EventLogProducer {
	void sendMessage(EventLogDTO eventLogDTO);
}
