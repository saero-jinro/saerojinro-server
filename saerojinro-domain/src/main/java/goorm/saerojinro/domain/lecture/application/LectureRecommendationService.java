package goorm.saerojinro.domain.lecture.application;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureRecommendationService {
	public Map<Category, Integer> getRecommendationCategoriesByCache(List<RedisLogEvent> redisLogEvents) {
		return getRecommendationCategories(redisLogEvents.stream()
			.collect(Collectors.groupingBy(RedisLogEvent::category,
				Collectors.summingInt(redisLogEvent -> redisLogEvent.logEventType().getWeight()))));
	}

	public Map<Category, Integer> getRecommendationCategoriesByEntity(List<LogEvent> logEvents) {
		return getRecommendationCategories(logEvents.stream()
			.collect(Collectors.groupingBy(LogEvent::getCategory,
				Collectors.summingInt(logEvent -> logEvent.getLogEventType().getWeight()))));
	}

	public Map<Long, Integer> getRecommendationLectureIds(List<LogEvent> logEvents) {
		return logEvents.stream()
			.collect(Collectors.groupingBy(
				logEvent -> logEvent.getLecture().getId(),
				Collectors.summingInt(logEvent -> logEvent.getLogEventType().getWeight())
			));
	}

	private Map<Category, Integer> getRecommendationCategories(Map<Category, Integer> categoryWeights) {
		List<Category> sortedCategories = categoryWeights.entrySet().stream()
			.sorted(Map.Entry.<Category, Integer>comparingByValue().reversed())
			.map(Map.Entry::getKey)
			.limit(3)
			.toList();

		return IntStream.range(0, sortedCategories.size())
			.boxed()
			.collect(Collectors.toMap(
				sortedCategories::get,
				index -> index + 1,
				(e1, e2) -> e1,
				LinkedHashMap::new
			));
	}
}