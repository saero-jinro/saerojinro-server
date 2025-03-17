package logevent.domain;

import static goorm.saerojinro.common.domain.Category.BACKEND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.enums.LogEventType;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;

public class LogEventDomainTest {
	@Test
	@DisplayName("create는 EventLog를 생성한다.")
	public void create_Success(){
		// given
		String record = "record";
		User user = User.builder().build();
		Lecture lecture = Lecture.builder().build();
		LogEventType logEventType = LogEventType.LECTURE_RESERVATION_SUCCESS;
		Category category = BACKEND;

		// when
		LogEvent result = LogEvent.create(record, user, lecture, logEventType, category, LocalDateTime.now());

		// then
		assertNotNull(record);
		assertEquals(record, result.getRecord());
		assertEquals(user, result.getUser());
		assertEquals(lecture, result.getLecture());
		assertEquals(logEventType, result.getLogEventType());
		assertEquals(category, result.getCategory());
	}
}
