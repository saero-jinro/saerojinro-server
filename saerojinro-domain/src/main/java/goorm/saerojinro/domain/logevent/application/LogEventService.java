package goorm.saerojinro.domain.logevent.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.LogEventRepository;
import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;
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
	public LogEvent save(String record, LogEventDto logEventDto) {
		User user = userQueryService.getById(logEventDto.userId());
		Lecture lecture = lectureQueryService.getByLectureId(logEventDto.lectureId());
		LogEvent logEvent = LogEvent.create(record, user, lecture, logEventDto.logEventType(), logEventDto.category());

		return logEventRepository.save(logEvent);
	}
}
