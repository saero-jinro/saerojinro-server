package goorm.saerojinro.api.notification.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.notification.domain.Notification;
import lombok.Builder;

// TODO swagger
@Builder
public record ReceivedNotificationResponse(
	Lecture lecture,
	String title,
	String contents
) {
	public static ReceivedNotificationResponse from(Notification notification) {
		return ReceivedNotificationResponse.builder()
			.lecture(notification.getLecture())
			.title(notification.getTitle())
			.contents(notification.getContents())
			.build();
	}
}
