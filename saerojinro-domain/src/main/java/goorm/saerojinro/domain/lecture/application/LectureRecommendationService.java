package goorm.saerojinro.domain.lecture.application;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureRecommendationService {
	public Map<Category, Integer> getRecommendationCategoriesByDto(List<LogEventDto> logEvents) {
		return getRecommendationCategories(logEvents.stream()
			.collect(Collectors.groupingBy(LogEventDto::category,
				Collectors.summingInt(dto -> dto.logEventType().getWeight()))));
	}

	public Map<Category, Integer> getRecommendationCategoriesByEntity(List<LogEvent> logEvents) {
		return getRecommendationCategories(logEvents.stream()
			.collect(Collectors.groupingBy(LogEvent::getCategory,
				Collectors.summingInt(logEvent -> logEvent.getLogEventType().getWeight()))));
	}

	private Map<Category, Integer> getRecommendationCategories(Map<Category, Integer> categoryWeights) {
		List<Category> sortedCategories = categoryWeights.entrySet().stream()
			.sorted(Map.Entry.<Category, Integer>comparingByValue().reversed())
			.map(Map.Entry::getKey)
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