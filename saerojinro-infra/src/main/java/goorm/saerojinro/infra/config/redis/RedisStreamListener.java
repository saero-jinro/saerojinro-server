package goorm.saerojinro.infra.config.redis;

import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import goorm.saerojinro.domain.eventlog.application.EventLogCommandService;
import goorm.saerojinro.domain.eventlog.domain.EventLog;
import goorm.saerojinro.domain.eventlog.domain.EventLogDTO;
import goorm.saerojinro.domain.eventlog.domain.EventLogRepository;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisStreamListener implements StreamListener<String, ObjectRecord<String, String>> {
	private final EventLogCommandService eventLogCommandService;
	private final UserQueryService userQueryService;
	private final LectureQueryService lectureQueryService;
	private final RedisTemplate<String, String> redisTemplate;
	private final ObjectMapper objectMapper;
	private static final String STREAM_KEY = "event_log_stream";

	@Override
	public void onMessage(ObjectRecord<String, String> message) {
		try {
			String record = String.valueOf(message.getId());
			EventLogDTO eventLogDTO = objectMapper.readValue(message.getValue(), EventLogDTO.class);
			User user = userQueryService.getById(eventLogDTO.userId());
			Lecture lecture = lectureQueryService.getByLectureId(eventLogDTO.lectureId());
			EventLog eventLog = EventLog.create(record, user, lecture, eventLogDTO.eventType(), eventLogDTO.category());

			eventLogCommandService.save(eventLog);

			redisTemplate.opsForStream().trim(STREAM_KEY, 1000);

		} catch (Exception e) {
			System.err.println("❌ 메시지 처리 실패: " + e.getMessage());
		}
	}
}
