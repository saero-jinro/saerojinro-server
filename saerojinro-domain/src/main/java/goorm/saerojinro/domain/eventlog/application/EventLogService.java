package goorm.saerojinro.domain.eventlog.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.domain.eventlog.domain.EventLog;
import goorm.saerojinro.domain.eventlog.domain.EventLogRepository;
import goorm.saerojinro.domain.eventlog.domain.dto.EventLogDTO;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventLogService {
	private final EventLogRepository eventLogRepository;
	private final UserQueryService userQueryService;
	private final LectureQueryService lectureQueryService;

	@Transactional
	public EventLog save(String record, EventLogDTO eventLogDTO) {
		User user = userQueryService.getById(eventLogDTO.userId());
		Lecture lecture = lectureQueryService.getByLectureId(eventLogDTO.lectureId());
		EventLog eventLog = EventLog.create(record, user, lecture, eventLogDTO.eventLogType(), eventLogDTO.category());

		return eventLogRepository.save(eventLog);
	}
}
