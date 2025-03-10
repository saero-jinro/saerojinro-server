package goorm.saerojinro.infra.messaging.eventlog;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import goorm.saerojinro.domain.eventlog.domain.EventLog;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.connection.stream.StreamRecords;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class EventLogProducer {
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;
	private static final String STREAM_KEY = "event_log_stream";

	public void sendEventLog(EventLog eventLog) {
		try {
			String eventLogJson = objectMapper.writeValueAsString(eventLog);

			RecordId recordId = redisTemplate.opsForStream().add(
				StreamRecords.newRecord()
					.ofObject(eventLogJson)
					.withStreamKey(STREAM_KEY)
			);

			if (Optional.ofNullable(recordId).isEmpty()) {
				throw new RuntimeException("Redis Stream 기록 실패: " + eventLog);
			}
		} catch (JsonProcessingException e) {
			throw new RuntimeException("JSON 직렬화 실패: " + e.getMessage());
		}
	}
}
