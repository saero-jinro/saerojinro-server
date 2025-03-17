package goorm.saerojinro.infra.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.LogEventRepository;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;
import goorm.saerojinro.infra.repository.jpa.LogEventJpaRepository;
import goorm.saerojinro.infra.repository.redis.RedisLogEventRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LogEventRepositoryImpl implements LogEventRepository {
	private final LogEventJpaRepository logEventJpaRepository;
	private final RedisLogEventRepository redisLogEventRepository;

	@Override
	public LogEvent save(LogEvent logEvent) {
		return logEventJpaRepository.save(logEvent);
	}

	@Override
	public List<LogEvent> findRecentLogByUserId(Long userId) {
		return logEventJpaRepository.findTop50ByUserIdOrderByTimestampDesc(userId);
	}

	@Override
	public List<RedisLogEvent> findRecentFromRedis(Long userId) {
		return redisLogEventRepository.findRecentFromCache(userId);
	}

	@Override
	public void cache(RedisLogEvent redisLogEvent) {
		redisLogEventRepository.addLogEvent(redisLogEvent);
	}

	@Override
	public List<LogEvent> findTop20ByLectureIdInOrderByTimestampDesc(List<Long> lectureIds) {
		return logEventJpaRepository.findTop20ByLectureIdInOrderByTimestampDesc(lectureIds);
	}
}
