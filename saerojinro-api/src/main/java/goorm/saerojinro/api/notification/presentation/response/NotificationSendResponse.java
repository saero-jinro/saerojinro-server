package goorm.saerojinro.api.notification.presentation.response;

import goorm.saerojinro.domain.notification.domain.Notification;
import lombok.Builder;

// TODO 스웨거추가
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
