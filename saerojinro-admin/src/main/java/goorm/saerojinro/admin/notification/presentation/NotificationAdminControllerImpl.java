package goorm.saerojinro.admin.notification.presentation;

import goorm.saerojinro.admin.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.common.event.CommonEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static goorm.saerojinro.common.event.EventType.LECTURE_NOTICE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notifications")
public class NotificationAdminControllerImpl implements NotificationAdminController {
	private final ApplicationEventPublisher eventPublisher;

	@Override
	@PostMapping("/all")
	public ResponseEntity<Void> sendAll(
		@RequestBody NotificationSendRequest request
	) {
		eventPublisher.publishEvent(
			CommonEvent.createBroadcast(request.title(), request.contents())
		);
		return ResponseEntity.noContent().build();
	}

	@Override
	@PostMapping("/lecture/{lectureId}")
	public ResponseEntity<Void> sendByLecture(
		@PathVariable Long lectureId,
		@RequestBody NotificationSendRequest request) {
		eventPublisher.publishEvent(
			CommonEvent.createWithLectureId(LECTURE_NOTICE, lectureId, request.title(), request.contents())
		);
		return ResponseEntity.noContent().build();
	}
}
