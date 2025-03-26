package goorm.saerojinro.domain.logevent.application;

import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.domain.lecture.application.dto.LectureCacheDTO;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.LogEventProducer;
import goorm.saerojinro.domain.logevent.domain.LogEventRepository;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.logevent.domain.enums.LogEventType;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogEventService {
	private final LogEventRepository logEventRepository;
	private final UserQueryService userQueryService;
	private final LectureQueryService lectureQueryService;
	private final LogEventProducer logEventProducer;

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

	@Transactional(readOnly = true)
	public List<LogEvent> getTop20ByLectureIdInOrderByTimestampDesc(List<Long> lectureIds){
		return logEventRepository.findTop20ByLectureIdInOrderByTimestampDesc(lectureIds);
	}

	public void sendLogEventFromCache(User user, LectureCacheDTO lectureCacheDTO, LogEventType logEventType) {
		if (user != null && user.getRole().equals(ATTENDEE)) {
			RedisLogEvent redisLogEvent = RedisLogEvent.of(
				user.getId(),
				lectureCacheDTO.id(),
				logEventType,
				lectureCacheDTO.category()
			);

			logEventProducer.sendMessage(redisLogEvent);
		}
	}

	public void sendLogEventFromEntity(User user, Lecture lecture, LogEventType logEventType) {
		if (user != null && user.getRole().equals(ATTENDEE)) {
			RedisLogEvent redisLogEvent = RedisLogEvent.of(
				user.getId(),
				lecture.getId(),
				logEventType,
				lecture.getCategory()
			);

			logEventProducer.sendMessage(redisLogEvent);
		}
	}
}
