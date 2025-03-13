package goorm.saerojinro.api.lecture.application;

import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_VIEW;

import goorm.saerojinro.api.lecture.presentation.response.*;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureRecommendationService;
import goorm.saerojinro.domain.logevent.application.LogEventService;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class LectureFacade {
	private final LectureQueryService lectureQueryService;
	private final LogEventProducer logEventProducer;
	private final UserQueryService userQueryService;
	private final LectureRecommendationService lectureRecommendationService;
	private final LogEventService logEventService;

	@Transactional(readOnly = true)
	public LectureListResponseByAll getAll() {
		List<Lecture> responses = lectureQueryService.getAllLecture();
		return LectureListResponseByAll.from(responses);
	}

	@Transactional(readOnly = true)
	public LectureDetailResponse getById(Long id) {
		Lecture lecture = lectureQueryService.getById(id);
		User user = userQueryService.me();

		if (user != null && user.getRole().equals(ATTENDEE)) {
			LogEventDto logEventDto = LogEventDto.of(user.getId(), lecture.getId(), LECTURE_VIEW, lecture.getCategory());
			logEventProducer.sendMessage(logEventDto);
		}

		return LectureDetailResponse.from(lecture);
	}

	@Transactional(readOnly = true)
	public LectureListResponseByDate getByDate(LocalDate localDate) {
		List<LectureResponseByDate> responses = lectureQueryService.getByDate(localDate).stream()
			.map(LectureResponseByDate::from)
			.toList();
		return LectureListResponseByDate.from(responses);
	}

	@Transactional(readOnly = true)
	public LectureSummaryListResponse getRecommendationLectures(LocalDateTime lectureStartTime) {
		List<LogEvent> userLogEvents = logEventService.getLogEventsByUser();
		Map<Category, Integer> categoryPriortyMap = lectureRecommendationService.getRecommendationCategories(userLogEvents);
		List<Lecture> getRecommendationLectures = lectureQueryService.getRecommendedLectureByDate(categoryPriortyMap, lectureStartTime);
		return LectureSummaryListResponse.from(getRecommendationLectures);
	}
}
