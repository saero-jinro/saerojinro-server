package goorm.saerojinro.api.notification.application;

import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.infra.notification.sse.NotificationSseSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationFacade {
	private final NotificationSseSender sseSender;
	private final NotificationQueryService notificationQueryService;
	private final UserQueryService userQueryService;

	public SseEmitter subscribe() {
		User user = userQueryService.me();
		return sseSender.subscribe(user.getId());
	}

	@Transactional(readOnly = true)
	public ReceivedNotificationListResponse myNotification() {
		User user = userQueryService.me();
		List<Notification> notificationList = notificationQueryService.findByUserId(user.getId());
		return ReceivedNotificationListResponse.from(notificationList);
	}
}
