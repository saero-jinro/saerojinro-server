package goorm.saerojinro.api.notification.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.notification.domain.Notification;
import lombok.Builder;

// TODO swagger
@Builder
public record ReceivedNotificationResponse(
	String title,
	String contents
) {
	public static ReceivedNotificationResponse from(Notification notification) {
		return ReceivedNotificationResponse.builder()
			.title(notification.getTitle())
			.contents(notification.getContents())
			.build();
	}
}
