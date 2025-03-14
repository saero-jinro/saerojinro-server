package goorm.saerojinro.api.lecture.application;

import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_VIEW;

import goorm.saerojinro.api.lecture.presentation.response.*;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureRecommendationService;
import goorm.saerojinro.domain.logevent.application.LogEventService;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;
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
	public LectureDetailResponse getById(Long id) {
		Lecture lecture = lectureQueryService.getById(id);
		User user = userQueryService.me();

		if (user != null && user.getRole().equals(ATTENDEE)) {
			RedisLogEvent redisLogEvent = RedisLogEvent.of(user.getId(), lecture.getId(), LECTURE_VIEW, lecture.getCategory(), LocalDateTime.now());
			logEventProducer.sendMessage(redisLogEvent);
		}

		return LectureDetailResponse.from(lecture);
	}

	@Transactional(readOnly = true)
	public LectureListResponse getByDate(LocalDate localDate) {
		List<LectureResponse> responses = lectureQueryService.getByDate(localDate).stream()
			.map(LectureResponse::from)
			.toList();
		return LectureListResponse.from(responses);
	}

	@Transactional(readOnly = true)
	public LectureSummaryListResponse getRecommendationLectures(LocalDateTime lectureStartTime) {
		Long userId = userQueryService.me().getId();
		Map<Category, Integer> categoryPriortyMap;
		List<RedisLogEvent> userRedisLogEvents = logEventService.getLogEventsByUserFromCache(userId);

		if (userRedisLogEvents.isEmpty()) {
			List<LogEvent> userLogEvents = logEventService.getLogEventsByUser(userId);
			categoryPriortyMap = lectureRecommendationService.getRecommendationCategoriesByEntity(userLogEvents);
		} else {
			categoryPriortyMap = lectureRecommendationService.getRecommendationCategoriesByDto(userRedisLogEvents);
		}

		List<Lecture> getRecommendationLectures = lectureQueryService.getRecommendedLectureByDate(categoryPriortyMap, lectureStartTime);
		return LectureSummaryListResponse.from(getRecommendationLectures);
	}
}
