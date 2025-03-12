package goorm.saerojinro.domain.lecture.application;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureRecommendationService {
	private final LectureRepository lectureRepository;

	public List<Category> getRecommendationCategories(List<LogEvent> logEvents) {
		return logEvents.stream()
			.collect(Collectors.groupingBy(LogEvent::getCategory,
				Collectors.summingInt(logEvent -> logEvent.getLogEventType().getWeight())))
			.entrySet().stream()
			.sorted(Map.Entry.<Category, Integer>comparingByValue().reversed())
			.map(Map.Entry::getKey)
			.toList();
	}
}