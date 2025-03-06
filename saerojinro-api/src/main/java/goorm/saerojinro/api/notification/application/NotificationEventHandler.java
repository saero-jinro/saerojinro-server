package goorm.saerojinro.api.notification.application;

import goorm.saerojinro.infra.notification.sse.NotificationSseSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventHandler {
	private final NotificationSseSender sseSender;


}
