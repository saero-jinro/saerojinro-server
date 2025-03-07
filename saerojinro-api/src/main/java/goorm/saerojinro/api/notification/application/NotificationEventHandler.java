package goorm.saerojinro.api.notification.application;

import goorm.saerojinro.infra.notification.request.NotificationSendRequest;
import goorm.saerojinro.common.event.CommonEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventHandler {
	private final NotificationFacade notificationFacade;

	@EventListener
	public void handleEvent(CommonEvent event) {
		log.error("현재는 일반 API에 알림을 발생시킬 수 없습니다. {}", event.eventType());
	}

	public NotificationSendRequest makeNotice(CommonEvent event) {
		String title = "[" + event.eventType().getDescription() + "]" + event.title();
		return NotificationSendRequest.of(title, event.contents());
	}
}
