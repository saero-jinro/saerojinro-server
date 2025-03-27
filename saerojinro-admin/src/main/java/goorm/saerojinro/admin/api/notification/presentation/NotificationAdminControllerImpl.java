package goorm.saerojinro.admin.api.notification.presentation;

import goorm.saerojinro.admin.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.common.event.CommonEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static goorm.saerojinro.common.event.EventType.LECTURE_NOTICE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/notifications")
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
		return ResponseEntity.status(CREATED).build();
	}

	@Override
	@PostMapping("/lectures/{id}")
	public ResponseEntity<Void> sendByLecture(
		@PathVariable Long id,
		@RequestBody NotificationSendRequest request) {
		eventPublisher.publishEvent(
			CommonEvent.createWithLectureId(LECTURE_NOTICE, id, request.title(), request.contents())
		);
		return ResponseEntity.status(CREATED).build();
	}
}
