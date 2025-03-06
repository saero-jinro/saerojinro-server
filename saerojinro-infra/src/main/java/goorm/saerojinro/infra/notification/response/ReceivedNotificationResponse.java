package goorm.saerojinro.infra.notification.response;

import goorm.saerojinro.domain.notification.domain.Notification;
import lombok.Builder;

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
