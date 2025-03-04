package goorm.saerojinro.api.notification.presentation;

import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationControllerImpl implements NotificationController {
	private final NotificationFacade notificationFacade;

	@Override
	@GetMapping("/subscribe")
	public ResponseEntity<SseEmitter> subscribe() {
		SseEmitter response = notificationFacade.subscribe();
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<ReceivedNotificationListResponse> myNotifications() {
		ReceivedNotificationListResponse response = notificationFacade.myNotification();
		return ResponseEntity.ok(response);
	}

	// TODO
	@Override
	public ResponseEntity<Void> sendAll() {
		return null;
	}

	@Override
	public ResponseEntity<Void> sendByLecture(Long lectureId) {
		return null;
	}

	@Override
	public ResponseEntity<Void> sendByUserId(Long userId) {
		return null;
	}
}
