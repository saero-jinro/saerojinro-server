package goorm.saerojinro.infra.messaging.eventlog;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;
import goorm.saerojinro.domain.logevent.domain.LogEventProducer;
import goorm.saerojinro.infra.config.redis.RedisProperties;
import goorm.saerojinro.infra.messaging.exception.InvalidMessageFormatException;
import goorm.saerojinro.infra.messaging.exception.StreamProcessingException;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class LogEventProducerImpl implements LogEventProducer {
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;
	private final RedisProperties redisProperties;

	@Override
	public void sendMessage(LogEventDto logEventDto) {
		try {
			String eventLogJson = objectMapper.writeValueAsString(logEventDto);

			ObjectRecord<String, String> record = StreamRecords.newRecord()
				.in(redisProperties.getLogEventStreamKey())
				.ofObject(eventLogJson);

			RecordId recordId = redisTemplate.opsForStream().add(record);

			if (Optional.ofNullable(recordId).isEmpty()) {
				throw new StreamProcessingException();
			}
		} catch (JsonProcessingException e) {
			throw new InvalidMessageFormatException();
		}
	}
}
