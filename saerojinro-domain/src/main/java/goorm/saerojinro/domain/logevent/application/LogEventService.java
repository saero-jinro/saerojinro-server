package goorm.saerojinro.domain.logevent.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.LogEventRepository;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogEventService {
	private final LogEventRepository logEventRepository;
	private final UserQueryService userQueryService;
	private final LectureQueryService lectureQueryService;

	@Transactional
	public LogEvent save(String record, RedisLogEvent redisLogEvent) {
		User user = userQueryService.getById(redisLogEvent.userId());
		Lecture lecture = lectureQueryService.getById(redisLogEvent.lectureId());
		LogEvent logEvent = LogEvent.create(
			record,
			user,
			lecture,
			redisLogEvent.logEventType(),
			redisLogEvent.category(),
			redisLogEvent.timestamp()
		);
		return logEventRepository.save(logEvent);
	}

	@Transactional(readOnly = true)
	public List<LogEvent> getLogEventsByUser(Long userId) {
		return logEventRepository.findRecentLogByUserId(userId);
	}

	public List<RedisLogEvent> getLogEventsByUserFromRedis(Long userId) {
		return logEventRepository.findRecentFromRedis(userId);
	}

	public void cache(RedisLogEvent logEvent) {
		logEventRepository.cache(logEvent);
	}
}
