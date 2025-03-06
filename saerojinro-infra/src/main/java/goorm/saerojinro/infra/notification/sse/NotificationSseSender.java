package goorm.saerojinro.infra.notification.sse;

import goorm.saerojinro.infra.notification.response.NotificationSendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class NotificationSseSender {
	public void sendNotification(SseEmitter emitter, NotificationSendResponse message) {
		try {
			emitter.send(SseEmitter.event().data(message));
		} catch (IOException e) {
			emitter.completeWithError(e);
		}
	}
}
