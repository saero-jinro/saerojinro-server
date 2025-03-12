package logevent.application;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.domain.Category.BACKEND;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_REGISTER;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.logevent.application.LogEventService;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeLogEventRepository;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeUserRepository;

public class LogEventServiceTest {
	private LogEventService logEventService;
	private User user;
	private Lecture lecture;

	@BeforeEach
	public void init() {
		FakeLogEventRepository fakeEventLogRepository = new FakeLogEventRepository();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		FakeLectureRepository fakeLectureRepository = new FakeLectureRepository();
		UserQueryService userQueryService = new UserQueryService(fakeUserRepository, new BCryptPasswordEncoder());
		LectureQueryService lectureQueryService = new LectureQueryService(fakeLectureRepository);

		logEventService = new LogEventService(
			fakeEventLogRepository,
			userQueryService,
			lectureQueryService
		);

		user = fakeUserRepository.save(User.builder()
			.email("email@email.com")
			.password("password1234!")
			.name("박민준")
			.role(ADMIN)
			.build()
		);

		lecture = fakeLectureRepository.save(Lecture.create(
			Speaker.builder().build(),
			"Lecture One",
			"Content One",
			"uploads/lecture/thumbnail",
			"uploads/lecture/materialsUri",
			100L,
			LocalDateTime.of(2025, 3, 1, 10, 0),
			LocalDateTime.of(2025, 3, 1, 12, 0),
			"Location One",
			Category.BACKEND
		));
	}

	@Test
	@DisplayName("save는 EventLog를 저장한다.")
	public void save_Success() {
		// given
		String record = "record";
		LogEventDto logEventDto = LogEventDto.of(1L, 1L, LECTURE_REGISTER, BACKEND);

		// when
		LogEvent response = logEventService.save(record, logEventDto);

		// then
		assertEquals(record, response.getRecord());
		assertEquals(logEventDto.logEventType(), response.getLogEventType());
		assertEquals(logEventDto.category(), response.getCategory());
		assertEquals(user, response.getUser());
		assertEquals(lecture, response.getLecture());
	}
}
