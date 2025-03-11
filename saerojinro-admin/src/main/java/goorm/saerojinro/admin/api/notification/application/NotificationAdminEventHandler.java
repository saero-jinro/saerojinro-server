package goorm.saerojinro.admin.api.notification.application;

import goorm.saerojinro.admin.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.common.event.CommonEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationAdminEventHandler {
	private final NotificationAdminFacade notificationFacade;

	@EventListener
	public void handleEvent(CommonEvent event) {
		switch (event.eventType()) {
			case BROADCAST_NOTICE -> broadcastNotice(event);
			case LECTURE_NOTICE -> lectureNotice(event);
			case LECTURE_IMMINENT -> lectureImminent(event);
		}
	}

	public void broadcastNotice(CommonEvent event) {
		notificationFacade.sendNotificationAll(makeNotice(event));
	}

	public void lectureNotice(CommonEvent event) {
		notificationFacade.sendNotificationByLectureId(event.lectureId(), makeNotice(event));
	}

	public void lectureImminent(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of(event.title(), event.contents());
		notificationFacade.sendNotificationByLectureId(event.lectureId(), request);
	}

	public NotificationSendRequest makeNotice(CommonEvent event) {
		String title = "[" + event.eventType().getDescription() + "]" + event.title();
		return NotificationSendRequest.of(title, event.contents());
	}
}
