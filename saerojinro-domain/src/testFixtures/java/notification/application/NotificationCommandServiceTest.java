package notification.application;

import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationCommandServiceTest {
	private NotificationCommandService notificationCommandService;
	private static Notification notification;

	@BeforeEach
	public void init() {
		NotificationRepository notificationRepository = new FakeNotificationRepository();
		notificationCommandService = new NotificationCommandService(notificationRepository);

		final String TITLE = "Notification Title";
		final String CONTENTS = "Notification Contents";
		User user = User.createAdmin("email@email.com", "password", "name");

		notification = Notification.createNotification(user, TITLE, CONTENTS);
	}

	@Test
	@DisplayName("save는 notification을 저장한다")
	public void save_Success() {
		// when
		Long saved = notificationCommandService.save(notification);

		// then
		assertEquals(1L, saved);
	}
}
