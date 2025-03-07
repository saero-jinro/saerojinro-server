package goorm.saerojinro.speaker.notification.presentation;

import goorm.saerojinro.infra.notification.application.NotificationFacade;
import goorm.saerojinro.infra.notification.request.NotificationSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/speakers/notifications")
public class NotificationSpeakerControllerImpl implements NotificationSpeakerController {
	private final NotificationFacade notificationFacade;

	@Override
	@PostMapping("/send-lecture/{lectureId}")
	public ResponseEntity<Void> sendByLecture(
		@PathVariable Long lectureId,
		@RequestBody NotificationSendRequest request) {
		notificationFacade.sendNotificationByLectureId(lectureId, request);
		return ResponseEntity.noContent().build();
	}
}
