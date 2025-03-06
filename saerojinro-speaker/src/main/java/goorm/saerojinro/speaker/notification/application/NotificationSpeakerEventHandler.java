package goorm.saerojinro.speaker.notification.application;

import goorm.saerojinro.common.event.CommonEvent;
import goorm.saerojinro.infra.notification.application.NotificationFacade;
import goorm.saerojinro.infra.notification.request.NotificationSendRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationSpeakerEventHandler {
	private final NotificationFacade notificationFacade;

	@EventListener
	public void handleEvent(CommonEvent event) {
		switch (event.eventType()) {
			case LECTURE_NOTICE -> lectureNotice(event);
			case SPEAKER_CREATE -> speakerCreated(event);
			case LECTURE_CREATE -> lectureCreated(event);
			case LECTURE_DELETE -> lectureDeleted(event);
		}
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

	public void lectureCreated(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of(
			"강의 등록 요청",
			"강의 등록 요청이 발생했습니다"
		);
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}

	private void lectureDeleted(CommonEvent event) {
		NotificationSendRequest request = NotificationSendRequest.of(
			"강의 삭제 요청",
			"강의 삭제 요청이 발생했습니다"
		);
		notificationFacade.sendNotificationByReceiverId(event.userId(), request);
	}

	public NotificationSendRequest makeNotice(CommonEvent event) {
		String title = "[" + event.eventType().getDescription() + "]" + event.title();
		return NotificationSendRequest.of(title, event.contents());
	}
}
