package notification;

import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationDomainTest {
	private Notification notification;

	private static final String TITLE = "Notification Title";
	private static final String CONTENTS = "Notification Contents";

	@BeforeEach
	public void init() {
		User user = User.createAdmin("email@email.com", "password", "name");
		notification = Notification.createNotification(user, TITLE, CONTENTS);
	}


	@Test
	@DisplayName("Notification을 성공적으로 생성한다")
	void createNotification_Success() {
		// then
		assertNotNull(notification, "Notification 객체가 null이면 안 됩니다.");
		assertEquals(TITLE, notification.getTitle());
		assertEquals(CONTENTS, notification.getContents());
	}
}
