package goorm.saerojinro.infra.notification.sse;

import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.infra.notification.response.NotificationSendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class NotificationSseSender {
	private final EmitterRepository emitterRepository;

	public SseEmitter subscribe(Long userId) {
		SseEmitter saved = emitterRepository.save(userId);
		sendNotification(saved,
			NotificationSendResponse.builder()
				.title("연결 완료")
				.contents("SSE 연결 완료")
				.build()
		);
		return saved;
	}

	public void sendNotification(SseEmitter emitter, NotificationSendResponse message) {
		try {
			emitter.send(SseEmitter.event().data(message));
		} catch (IOException e) {
			emitter.completeWithError(e);
		}
	}
}
