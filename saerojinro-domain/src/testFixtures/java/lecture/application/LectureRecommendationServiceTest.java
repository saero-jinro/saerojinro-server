package lecture.application;

import static goorm.saerojinro.common.domain.Category.BACKEND;
import static goorm.saerojinro.common.domain.Category.DEVOPS;
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
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;

public class LectureRecommendationServiceTest {
	private LectureRecommendationService lectureRecommendationService;
	private final List<LogEvent> logEvents = new ArrayList<>();

	@BeforeEach
	public void init() {
		lectureRecommendationService = new LectureRecommendationService();

		Lecture backendLecture = Lecture.builder()
			.id(1L)
			.category(DEVOPS)
			.build();

		Lecture devopsLecture = Lecture.builder()
			.id(2L)
			.category(DEVOPS)
			.build();

		logEvents.add(LogEvent.create(
			"record0",
			null,
			devopsLecture,
			LECTURE_RESERVATION_FAIL,
			DEVOPS,
			LocalDateTime.now())
		);

		logEvents.add(LogEvent.create(
			"record1",
			null,
			devopsLecture,
			LECTURE_RESERVATION_FAIL,
			DEVOPS,
			LocalDateTime.now())
		);

		logEvents.add(LogEvent.create(
			"record2",
			null,
			backendLecture,
			LECTURE_RESERVATION_SUCCESS,
			BACKEND,
			LocalDateTime.now())
		);
	}

	@Test
	@DisplayName("getRecommendationCategoriesByEntity는 logEvents를 받아 각 카테고리에 대한 가중치 총합을 도출한다.")
	public void getRecommendationCategoriesByEntity_Success() {
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
			DEVOPS
			)
		);

		logEvents.add(RedisLogEvent.of(
			null,
			null,
			LECTURE_RESERVATION_FAIL,
			DEVOPS
			)
		);

		logEvents.add(RedisLogEvent.of(
			null,
			null,
			LECTURE_RESERVATION_SUCCESS,
			BACKEND
			)
		);

		// when
		Map<Category, Integer> result = lectureRecommendationService
			.getRecommendationCategoriesByCache(logEvents);

		// then
		assertEquals(result.get(BACKEND), 2);
		assertEquals(result.get(DEVOPS), 1);
	}

	@Test
	@DisplayName("getRecommendationLectureIds는 logEvents를 기반으로 해당 강의에 대한 가중치 결과 Map을 반환한다.")
	public void getRecommendationLectureIds_Success() {
		// when
		Map<Long, Integer> result = lectureRecommendationService
			.getRecommendationLectureIds(logEvents);

		// then
		assertEquals(12, result.get(2L));
		assertEquals(10, result.get(1L));
	}
}
