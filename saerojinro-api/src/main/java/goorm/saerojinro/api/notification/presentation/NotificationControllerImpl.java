package goorm.saerojinro.api.notification.presentation;

import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationControllerImpl implements NotificationController {
	private final NotificationFacade notificationFacade;

	@Override
	@GetMapping("/subscribe")
	public ResponseEntity<Void> subscribe() {
		notificationFacade.subscribe();
		return ResponseEntity.status(CREATED).build();
	}

	@Override
	@GetMapping("/my")
	public ResponseEntity<ReceivedNotificationListResponse> myNotifications() {
		ReceivedNotificationListResponse response = notificationFacade.myNotification();
		return ResponseEntity.ok(response);
	}
}
