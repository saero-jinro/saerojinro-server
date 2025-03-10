package goorm.saerojinro.api.lecture.application;

import static goorm.saerojinro.domain.eventlog.domain.enums.EventLogType.LECTURE_VIEW;

import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureResponse;
import goorm.saerojinro.domain.eventlog.domain.dto.EventLogDTO;
import goorm.saerojinro.domain.eventlog.domain.EventLogProducer;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional(readOnly = true)
	public LectureDetailResponse getByLectureId(long lectureId) {
		Lecture lecture = lectureService.getByLectureId(lectureId);
		User user = userQueryService.me();

		if (user != null) {
			EventLogDTO eventLogDTO = EventLogDTO.of(user.getId(), lecture.getId(), LECTURE_VIEW, lecture.getCategory());
			eventLogProducer.sendMessage(eventLogDTO);
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
