package goorm.saerojinro.domain.logevent.domain;

import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;

public interface LogEventProducer {
	void sendMessage(LogEventDto logEventDto);
}
