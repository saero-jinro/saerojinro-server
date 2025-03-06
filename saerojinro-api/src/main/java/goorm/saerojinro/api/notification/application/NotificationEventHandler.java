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

	public void broadcastNotice(CommonEvent event) {
		notificationFacade.sendNotificationAll(makeNotice(event));
	}

	public void lectureNotice(CommonEvent event) {
		notificationFacade.sendNotificationByLectureId(event.lectureId(), makeNotice(event));
	}

	public void speakerCreated(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of(
			"강연자 승인 요청",
			"강연자 승인 요청이 발생했습니다"
		);
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}

	public void speakerApproved(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of(
			"강연자 승인",
			"강연자 승인이 완료되어 강연자 권한을 획득했습니다"
		);
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}

	public void lectureCreated(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of(
			"강의 등록 승인 요청",
			"강의 등록 승인 요청이 발생했습니다"
		);
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}

	public void lectureApproved(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of(
			"강의 등록 승인",
			"강의 등록 승인이 완료되었습니다"
		);
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}

	public NotificationSendRequest makeNotice(CommonEvent event) {
		String title = "[" + event.eventType().getDescription() + "]" + event.title();
		return NotificationSendRequest.of(title, event.contents());
	}
}
