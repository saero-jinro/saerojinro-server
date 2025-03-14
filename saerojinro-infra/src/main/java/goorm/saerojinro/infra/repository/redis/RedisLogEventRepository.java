package goorm.saerojinro.infra.repository.redis;

import static java.util.concurrent.TimeUnit.HOURS;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;
import goorm.saerojinro.infra.messaging.exception.StreamProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisLogEventRepository {
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;

	private static final String USER_LOG_KEY_PREFIX = "user:logs:";

	public List<LogEventDto> findRecentFromCache(Long userId) {
		String redisKey = USER_LOG_KEY_PREFIX + userId;

		Set<String> cachedLogs = redisTemplate.opsForZSet().range(redisKey, 0, -1);
		List<LogEventDto> logEvents = new ArrayList<>();

		if (cachedLogs != null) {
			for (String json : cachedLogs) {
				try {
					logEvents.add(objectMapper.readValue(json, LogEventDto.class));
				} catch (JsonProcessingException e) {
					throw new StreamProcessingException();
				}
			}
		}

		return logEvents;
	}

	public void addLogEvent(LogEventDto logEventDto) {
		String redisKey = USER_LOG_KEY_PREFIX + logEventDto.userId();

		try {
			String json = objectMapper.writeValueAsString(logEventDto);
			long score = logEventDto.timestamp().toInstant(ZoneOffset.UTC).toEpochMilli();

			redisTemplate.opsForZSet().add(redisKey, json, score);
			redisTemplate.opsForZSet().removeRange(redisKey, 0, -51);
			redisTemplate.expire(redisKey, 6, HOURS);
		} catch (JsonProcessingException e) {
			throw new StreamProcessingException();
		}
	}
}
