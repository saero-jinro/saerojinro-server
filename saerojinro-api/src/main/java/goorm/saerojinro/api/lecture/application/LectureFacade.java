package goorm.saerojinro.api.lecture.application;

import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_VIEW;

import goorm.saerojinro.api.lecture.presentation.response.*;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureRecommendationService;
import goorm.saerojinro.domain.lecture.application.dto.LectureCacheDTO;
import goorm.saerojinro.domain.lecture.application.dto.LectureCacheListDTO;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LectureFacade {
	private final LectureQueryService lectureQueryService;
	private final LogEventProducer logEventProducer;
	private final UserQueryService userQueryService;
	private final LectureRecommendationService lectureRecommendationService;
	private final LogEventService logEventService;

	private static final int RECOMMENDATION_LIMIT = 3;

	@Transactional(readOnly = true)
	public LectureDetailResponse getById(Long id) {
		LectureCacheDTO lectureCacheDTO = lectureQueryService.getByIdCached(id);
		User user = userQueryService.me();

		if (user != null && user.getRole().equals(ATTENDEE)) {
			RedisLogEvent redisLogEvent = RedisLogEvent.of(user.getId(), lectureCacheDTO.id(), LECTURE_VIEW,
				lectureCacheDTO.category(), LocalDateTime.now());
			logEventProducer.sendMessage(redisLogEvent);
		}

		return LectureDetailResponse.from(lectureCacheDTO);
	}

	@Transactional(readOnly = true)
	public LectureListResponse getByDate(LocalDate localDate) {
		LectureCacheListDTO lectureCacheListDTO = lectureQueryService.getByDate(localDate);

		List<LectureResponse> responses = lectureCacheListDTO.lectureCacheListDTO()
			.stream()
			.map(LectureResponse::from)
			.toList();
		return LectureListResponse.from(responses);
	}

	@Transactional(readOnly = true)
	public LectureSummaryListResponse getRecommendationLectures(LocalDateTime lectureStartTime) {
		Long userId = userQueryService.me().getId();
		Map<Category, Integer> categoryPriortyMap = getCategoryPriorityMap(userId);

		List<Lecture> recommendedLectures = lectureQueryService.getRecommendedLectureByDate(categoryPriortyMap,
			lectureStartTime);

		if (recommendedLectures.size() < RECOMMENDATION_LIMIT) {
			recommendedLectures = fillWithPopularLectures(recommendedLectures, lectureStartTime);
		}

		return LectureSummaryListResponse.from(recommendedLectures);
	}

	private Map<Category, Integer> getCategoryPriorityMap(Long userId) {
		List<RedisLogEvent> userRedisLogEvents = logEventService.getLogEventsByUserFromRedis(userId);

		if (!userRedisLogEvents.isEmpty()) {
			return lectureRecommendationService.getRecommendationCategoriesByCache(userRedisLogEvents);
		}

		List<LogEvent> userLogEvents = logEventService.getLogEventsByUser(userId);
		return lectureRecommendationService.getRecommendationCategoriesByEntity(userLogEvents);
	}


	private List<Lecture> fillWithPopularLectures(List<Lecture> recommendedLectures, LocalDateTime lectureStartTime) {
		int remainingSlots = RECOMMENDATION_LIMIT - recommendedLectures.size();

		List<Lecture> lecturesByStartTime =
			lectureQueryService.getByStartTime(lectureStartTime);
		List<Long> lectureIds = lecturesByStartTime.stream()
			.map(Lecture::getId)
			.toList();
		Set<Long> recommendedLectureIds = recommendedLectures.stream()
			.map(Lecture::getId)
			.collect(Collectors.toSet());

		List<LogEvent> recentLogsByLecturesId =
			logEventService.getTop20ByLectureIdInOrderByTimestampDesc(lectureIds);

		Map<Long, Integer> lectureWeightMap =
			lectureRecommendationService.getRecommendationLectureIds(recentLogsByLecturesId);

		List<Lecture> filteredLectures = lecturesByStartTime.stream()
			.filter(lecture -> !recommendedLectureIds.contains(lecture.getId()))
			.sorted(
				Comparator.comparingInt(
					lecture -> -lectureWeightMap.getOrDefault(lecture.getId(), 0)
				)
			)
			.limit(remainingSlots)
			.toList();

		List<Lecture> mutableLectures = new ArrayList<>(recommendedLectures);
		mutableLectures.addAll(filteredLectures);

		return mutableLectures;
	}
}