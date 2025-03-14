package lecture.application;

import static goorm.saerojinro.common.domain.Category.BACKEND;
import static goorm.saerojinro.common.domain.Category.DEVOPS;
import static goorm.saerojinro.common.domain.Category.FRONTEND;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_RESERVATION_FAIL;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_RESERVATION_SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureRecommendationService;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;

public class LectureRecommendationServiceTest {
	private LectureRecommendationService lectureRecommendationService;

	@BeforeEach
	public void init() {
		lectureRecommendationService = new LectureRecommendationService();
	}

	@Test
	@DisplayName("getRecommendationCategoriesByEntity는 logEvents를 받아 각 카테고리에 대한 가중치 총합을 도출한다.")
	public void getRecommendationCategoriesByEntity_Success() {
		// given
		List<LogEvent> logEvents = new ArrayList<>();

		logEvents.add(LogEvent.create(
			"record0",
			null,
			null,
			LECTURE_RESERVATION_FAIL,
			DEVOPS,
			LocalDateTime.now())
		);

		logEvents.add(LogEvent.create(
			"record1",
			null,
			null,
			LECTURE_RESERVATION_FAIL,
			DEVOPS,
			LocalDateTime.now())
		);

		logEvents.add(LogEvent.create(
			"record2",
			null,
			null,
			LECTURE_RESERVATION_SUCCESS,
			BACKEND,
			LocalDateTime.now())
		);

		// when
		Map<Category, Integer> result = lectureRecommendationService
			.getRecommendationCategoriesByEntity(logEvents);

		// then
		assertEquals(result.get(BACKEND), 2);
		assertEquals(result.get(DEVOPS), 1);
	}

	@Test
	@DisplayName("getRecommendationCategoriesByCache는 RedislogEvents를 받아 각 카테고리에 대한 가중치 총합을 도출한다.")
	public void getRecommendationCategoriesByCache_Success() {
		// given
		List<RedisLogEvent> logEvents = new ArrayList<>();

		logEvents.add(RedisLogEvent.of(
			null,
			null,
			LECTURE_RESERVATION_FAIL,
			DEVOPS,
			LocalDateTime.now())
		);

		logEvents.add(RedisLogEvent.of(
			null,
			null,
			LECTURE_RESERVATION_FAIL,
			DEVOPS,
			LocalDateTime.now())
		);

		logEvents.add(RedisLogEvent.of(
			null,
			null,
			LECTURE_RESERVATION_SUCCESS,
			BACKEND,
			LocalDateTime.now())
		);

		// when
		Map<Category, Integer> result = lectureRecommendationService
			.getRecommendationCategoriesByCache(logEvents);

		// then
		assertEquals(result.get(BACKEND), 2);
		assertEquals(result.get(DEVOPS), 1);
	}
}
