package notification.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import mock.repository.FakeNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationCommandServiceTest {
	private NotificationCommandService notificationCommandService;
	private static Notification notification;

	@BeforeEach
	public void init() {
		NotificationRepository notificationRepository = new FakeNotificationRepository();
		notificationCommandService = new NotificationCommandService(notificationRepository);

		final String LECTURE_TITLE = "Title";
		final String LECTURE_CONTENTS = "Contents";
		final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
		final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
		final String LOCATION = "Location";
		final Category CATEGORY = Category.BACKEND;
		final LectureStatus STATUS = LectureStatus.PENDING_APPROVAL;

		Lecture lecture = Lecture.createLecture(
			LECTURE_TITLE, LECTURE_CONTENTS, START_TIME, END_TIME, LOCATION, CATEGORY, STATUS
		);

		final String TITLE = "Notification Title";
		final String CONTENTS = "Notification Contents";
		notification = Notification.createNotification(lecture, TITLE, CONTENTS);
	}

	@Test
	@DisplayName("save는 notification을 저장한다")
	public void save_Success() {
		Long saved = notificationCommandService.save(notification);
		assertEquals(1L, saved);
	}
}
