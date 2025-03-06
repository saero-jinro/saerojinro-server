package goorm.saerojinro.api.notification.application;

import goorm.saerojinro.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.common.event.CommonEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NotificationEventHandler {
	private final NotificationFacade notificationFacade;

	@EventListener
	public void handleEvent(CommonEvent event) {
		switch (event.eventType()) {
			case BROADCAST_NOTICE -> broadcastNotice(event);
			case LECTURE_NOTICE -> lectureNotice(event);
			case SPEAKER_CREATE -> speakerCreated(event);
			case SPEAKER_APPROVED -> speakerApproved(event);
			case LECTURE_CREATE -> lectureCreated(event);
			case LECTURE_APPROVE -> lectureApproved(event);
		}
	}

	// TODO 전체 공지나 강의 공지는 사용자에게서 내용을 받아와야함
	private void broadcastNotice(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of(event.title(), event.contents());
		notificationFacade.sendNotificationAll(request);
	}

	private void lectureNotice(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of(event.title(), event.contents());
		notificationFacade.sendNotificationByLectureId(event.lectureId(), request);
	}

	// TODO 생성에 대한 알림은 내부에서 만들거어서 보낼 수 있음
	private void speakerCreated(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of("", "");
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}

	private void speakerApproved(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of("", "");
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}

	private void lectureCreated(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of("", "");
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}

	private void lectureApproved(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of("", "");
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}
}
