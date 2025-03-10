package eventlog.application;

import static goorm.saerojinro.common.domain.Category.BACKEND;
import static goorm.saerojinro.domain.eventlog.domain.enums.EventLogType.LECTURE_REGISTER;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.eventlog.application.EventLogService;
import goorm.saerojinro.domain.eventlog.domain.EventLog;
import goorm.saerojinro.domain.eventlog.domain.dto.EventLogDTO;
import goorm.saerojinro.domain.eventlog.domain.enums.EventLogType;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeEventLogRepository;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeUserRepository;

public class EventLogServiceTest {
	private EventLogService eventLogService;
	private User user;
	private Lecture lecture;
	@BeforeEach
	public void init() {
		FakeEventLogRepository fakeEventLogRepository = new FakeEventLogRepository();
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		FakeLectureRepository fakeLectureRepository = new FakeLectureRepository();
		UserQueryService userQueryService = new UserQueryService(fakeUserRepository, new BCryptPasswordEncoder());
		LectureQueryService lectureQueryService = new LectureQueryService(fakeLectureRepository);

		eventLogService = new EventLogService(
			fakeEventLogRepository,
			userQueryService,
			lectureQueryService
		);
	}

	@Test
	@DisplayName("save는 EventLog를 저장한다.")
	public void save_Success() {
		// given
		String record = "record";
		EventLogDTO eventLogDTO = EventLogDTO.of(1L, 1L, LECTURE_REGISTER, BACKEND);

		// when
		EventLog response = eventLogService.save(record, eventLogDTO);

		// then
	}
}
