package lecture.application;

import static goorm.saerojinro.common.domain.Category.BACKEND;
import static goorm.saerojinro.common.domain.Category.DEVOPS;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_RESERVATION_FAIL;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_RESERVATION_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureRecommendationService;
import goorm.saerojinro.domain.logevent.domain.LogEvent;

public class LectureRecommendationServiceTest {
	private LectureRecommendationService lectureRecommendationService;

	@BeforeEach
	public void init() {
		lectureRecommendationService = new LectureRecommendationService();
	}

	@Test
	@DisplayName("getRecommendationCategories는 logEvents를 받아 각 카테고리에 대한 가중치 총합을 도출한다.")
	public void getRecommendationCategories_Success() {
		// given
		List<LogEvent> logEvents = new ArrayList<>();

		logEvents.add(LogEvent.create("record1", null, null, LECTURE_RESERVATION_FAIL, DEVOPS));
		logEvents.add(LogEvent.create("record2", null, null, LECTURE_RESERVATION_SUCCESS, BACKEND));

		// when
		Map<Category, Integer> result = lectureRecommendationService.getRecommendationCategories(logEvents);

		// then
		assertEquals(result.get(BACKEND), 1);
		assertEquals(result.get(DEVOPS), 2);
	}
}
