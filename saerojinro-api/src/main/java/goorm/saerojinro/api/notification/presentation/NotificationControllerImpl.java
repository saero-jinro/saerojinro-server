package goorm.saerojinro.api.notification.presentation;

import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	@GetMapping("/my")
	public ResponseEntity<ReceivedNotificationListResponse> myNotifications() {
		ReceivedNotificationListResponse response = notificationFacade.myNotification();
		return ResponseEntity.ok(response);
	}

	@Override
	@PostMapping("/send-all")
	public ResponseEntity<Void> sendAll(
		@RequestBody NotificationSendRequest request
	) {
		notificationFacade.sendNotificationAll(request);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PostMapping("/send-lecture/{lectureId}")
	public ResponseEntity<Void> sendByLecture(
		@PathVariable Long lectureId,
		@RequestBody NotificationSendRequest request) {
		notificationFacade.sendNotificationByLectureIdWithRequest(lectureId, request);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PostMapping("/send-user/{userId}")
	public ResponseEntity<Void> sendByUserId(
		@PathVariable Long userId,
		@RequestBody NotificationSendRequest request) {
		notificationFacade.sendNotificationWithRequest(userId, request);
		return ResponseEntity.noContent().build();
	}
}
