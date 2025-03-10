package goorm.saerojinro.infra.config.redis;

import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import goorm.saerojinro.domain.eventlog.domain.EventLog;
import goorm.saerojinro.domain.eventlog.domain.EventLogRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisStreamListener implements StreamListener<String, ObjectRecord<String, String>> {
	private final EventLogRepository eventLogRepository;
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;
	private static final String STREAM_KEY = "event_log_stream";

	@Override
	public void onMessage(ObjectRecord<String, String> message) {
		try {
			// ✅ JSON 문자열을 EventLog 객체로 변환
			EventLog eventLog = objectMapper.readValue(message.getValue(), EventLog.class);

			// ✅ 저장
			eventLogRepository.save(eventLog);
			System.out.println("✅ 로그 저장 완료: " + eventLog);

			// ✅ Redis Stream에서 메시지 삭제 (최대 1000개 유지)
			redisTemplate.opsForStream().trim(STREAM_KEY, 1000);

		} catch (Exception e) {
			System.err.println("❌ 메시지 처리 실패: " + e.getMessage());
		}
	}
}
