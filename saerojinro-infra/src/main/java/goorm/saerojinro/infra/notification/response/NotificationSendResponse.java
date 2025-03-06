package goorm.saerojinro.infra.notification.response;

import goorm.saerojinro.domain.notification.domain.Notification;
import lombok.Builder;

@Builder
public record NotificationSendResponse(
	String title,
	String contents
) {
	public static NotificationSendResponse from(Notification notification) {
		return NotificationSendResponse.builder()
			.title(notification.getTitle())
			.contents(notification.getContents())
			.build();
	}
}