package goorm.saerojinro.domain.logevent.domain;

import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;

public interface LogEventProducer {
	void sendMessage(RedisLogEvent redisLogEvent);
}
