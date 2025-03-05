package notification.application;

import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotificationQueryServiceTest {
	private NotificationQueryService notificationQueryService;

	private static final Long USER_ID = 1L;

	private static final String TITLE = "Notification Title";
	private static final String CONTENTS = "Notification Contents";

	@BeforeEach
	public void init() {
		NotificationRepository notificationRepository = new FakeNotificationRepository();
		notificationQueryService = new NotificationQueryService(notificationRepository);

		User user = User.builder().id(USER_ID).build();
		Notification notification = Notification.createNotification(user, TITLE, CONTENTS);

		notificationRepository.save(notification);
	}

	@Test
	@DisplayName("findByUserId는 notification을 조회한다")
	public void findByUserId_Success() {
		// when
		Notification notification = notificationQueryService.findByUserId(USER_ID).get(0);

		// then
		assertEquals(1L, notification.getId());
		assertEquals(TITLE, notification.getTitle());
		assertEquals(CONTENTS, notification.getContents());
	}

	@Test
	@DisplayName("findByUserId는 userId가 존재하지 않을 때 빈 리스트를 반환한다")
	public void findByUserId_return_Empty() {
		// when
		List<Notification> notifications = notificationQueryService.findByUserId(100L);

		// then
		assertEquals(0, notifications.size());
	}
}
