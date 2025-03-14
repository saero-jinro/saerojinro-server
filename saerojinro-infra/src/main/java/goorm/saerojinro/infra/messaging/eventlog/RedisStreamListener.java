package goorm.saerojinro.infra.messaging.eventlog;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import goorm.saerojinro.domain.logevent.application.LogEventService;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;
import goorm.saerojinro.infra.config.redis.RedisProperties;
import goorm.saerojinro.infra.messaging.exception.InvalidMessageFormatException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisStreamListener implements StreamListener<String, ObjectRecord<String, String>> {
	private final LogEventService logEventService;
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;
	private final RedisProperties redisProperties;

	@Override
	public void onMessage(ObjectRecord<String, String> message) {
		try {
			String record = String.valueOf(message.getId());
			RedisLogEvent redisLogEvent = objectMapper.readValue(message.getValue(), RedisLogEvent.class);

			logEventService.save(record, redisLogEvent);
			logEventService.cache(redisLogEvent);
			redisTemplate.opsForStream().trim(redisProperties.getLogEventStreamKey(), 1000);
		} catch (JsonProcessingException e) {
			throw new InvalidMessageFormatException();
		}
	}
}
