package notification;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationDomainTest {
	private Notification notification;

	private static final String TITLE = "Notification Title";
	private static final String CONTENTS = "Notification Contents";

	@BeforeEach
	public void init() {
		final User SPEAKER = User.builder()
			.id(1L)
			.name("Test Speaker")
			.role(BaseRole.SPEAKER)
			.build();
		final String LECTURE_TITLE = "Title";
		final String LECTURE_CONTENTS = "Contents";
		final Long MAX_CAPACITY = 100L;
		final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
		final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
		final String LOCATION = "Location";
		final Category CATEGORY = Category.BACKEND;

		Lecture lecture = Lecture.createLecture(
			SPEAKER, LECTURE_TITLE, LECTURE_CONTENTS, MAX_CAPACITY, START_TIME, END_TIME, LOCATION, CATEGORY
		);

		notification = Notification.createNotification(lecture, TITLE, CONTENTS);
	}


	@Test
	@DisplayName("Notification을 성공적으로 생성한다")
	void createNotification_Success() {
		assertNotNull(notification, "Notification 객체가 null이면 안 됩니다.");
		assertEquals(TITLE, notification.getTitle());
		assertEquals(CONTENTS, notification.getContents());
	}
}
