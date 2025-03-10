package eventlog.domain;

import static goorm.saerojinro.common.domain.Category.BACKEND;
import static goorm.saerojinro.domain.eventlog.domain.enums.EventLogType.LECTURE_REGISTER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.eventlog.domain.EventLog;
import goorm.saerojinro.domain.eventlog.domain.enums.EventLogType;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;

public class EventLogDomainTest {
	@Test
	@DisplayName("create는 EventLog를 생성한다.")
	public void create_Success(){
		// given
		String record = "record";
		User user = User.builder().build();
		Lecture lecture = Lecture.builder().build();
		EventLogType eventLogType = LECTURE_REGISTER;
		Category category = BACKEND;

		// when
		EventLog result = EventLog.create(record, user, lecture, eventLogType, category);

		// then
		assertNotNull(record);
		assertEquals(record, result.getRecord());
		assertEquals(user, result.getUser());
		assertEquals(lecture, result.getLecture());
		assertEquals(eventLogType, result.getEventLogType());
		assertEquals(category, result.getCategory());
	}
}
