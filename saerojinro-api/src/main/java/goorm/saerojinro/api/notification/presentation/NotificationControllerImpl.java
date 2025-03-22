package goorm.saerojinro.api.notification.presentation;

import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationControllerImpl implements NotificationController {
	private final NotificationFacade notificationFacade;

	@Override
	@PostMapping("/subscribe")
	public ResponseEntity<SseEmitter> subscribe() {
		SseEmitter response = notificationFacade.subscribe();
		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("/me")
	public ResponseEntity<ReceivedNotificationListResponse> myNotifications() {
		ReceivedNotificationListResponse response = notificationFacade.myNotification();
		return ResponseEntity.ok(response);
	}
}
