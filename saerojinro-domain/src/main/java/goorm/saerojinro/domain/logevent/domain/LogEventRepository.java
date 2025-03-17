package goorm.saerojinro.domain.logevent.domain;

import java.util.List;

import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;

public interface LogEventRepository {
	LogEvent save(LogEvent logEvent);

	List<LogEvent> findRecentLogByUserId(Long userId);

	List<RedisLogEvent> findRecentFromRedis(Long userId);

	void cache(RedisLogEvent redisLogEvent);

	List<LogEvent> findTop20ByLectureIdInOrderByTimestampDesc(List<Long> lectureIds);
}
