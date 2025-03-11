package goorm.saerojinro.api.lecture.application;

import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_VIEW;

import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureResponse;
import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;
import goorm.saerojinro.domain.logevent.domain.LogEventProducer;
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
	private final LogEventProducer logEventProducer;
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

		if (user != null && user.getRole().equals(ATTENDEE)) {
			LogEventDto logEventDto = LogEventDto.of(user.getId(), lecture.getId(), LECTURE_VIEW, lecture.getCategory());
			logEventProducer.sendMessage(logEventDto);
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
