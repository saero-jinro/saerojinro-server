package goorm.saerojinro.api.lecture.application;

import static goorm.saerojinro.domain.eventlog.domain.EventType.LECTURE_VIEW;

import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureResponse;
import goorm.saerojinro.domain.eventlog.domain.EventLog;
import goorm.saerojinro.domain.eventlog.domain.EventType;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.infra.messaging.eventlog.EventLogProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LectureFacade {
	private final LectureQueryService lectureService;
	private final EventLogProducer eventLogProducer;
	private final UserQueryService userQueryService;

	public LectureListResponse getAllLecture() {
		List<LectureResponse> responses = lectureService.getAllLecture().stream()
			.map(LectureResponse::from)
			.toList();

		return LectureListResponse.from(responses);
	}

	public LectureDetailResponse getByLectureId(long lectureId) {
		Lecture lecture = lectureService.getByLectureId(lectureId);
		User user = userQueryService.me();

		if (user != null) {
			EventLog eventLog = EventLog.create(user, lecture, LECTURE_VIEW);
			eventLogProducer.sendEventLog(eventLog);
		}

		return LectureDetailResponse.from(lecture);
	}

	public LectureListResponse getByDate(LocalDate localDate) {
		List<LectureResponse> responses = lectureService.getByDate(localDate).stream()
			.map(LectureResponse::from)
			.toList();
		return LectureListResponse.from(responses);
	}
}
