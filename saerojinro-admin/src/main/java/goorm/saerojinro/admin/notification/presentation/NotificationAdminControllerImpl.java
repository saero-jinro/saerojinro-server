package goorm.saerojinro.admin.notification.presentation;

import goorm.saerojinro.admin.notification.application.NotificationAdminFacade;
import goorm.saerojinro.admin.notification.presentation.request.NotificationSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notifications")
public class NotificationAdminControllerImpl implements NotificationAdminController {
	private final NotificationAdminFacade notificationFacade;

	@Override
	@PostMapping("/all")
	public ResponseEntity<Void> sendAll(
		@RequestBody NotificationSendRequest request
	) {
		notificationFacade.sendNotificationAll(request);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PostMapping("/lecture/{lectureId}")
	public ResponseEntity<Void> sendByLecture(
		@PathVariable Long lectureId,
		@RequestBody NotificationSendRequest request) {
		notificationFacade.sendNotificationByLectureId(lectureId, request);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PostMapping("/user/{userId}")
	public ResponseEntity<Void> sendByUserId(
		@PathVariable Long userId,
		@RequestBody NotificationSendRequest request) {
		notificationFacade.sendNotificationByReceiverId(userId, request);
		return ResponseEntity.noContent().build();
	}
}
