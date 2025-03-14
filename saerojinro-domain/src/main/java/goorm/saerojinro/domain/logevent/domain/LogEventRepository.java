package goorm.saerojinro.domain.logevent.domain;

import java.util.List;

import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;

public interface LogEventRepository {
	LogEvent save(LogEvent logEvent);

	List<LogEvent> findRecentLogByUserId(Long userId);

	List<RedisLogEvent> findRecentFromCache(Long userId);

	void cache(RedisLogEvent redisLogEvent);
}
