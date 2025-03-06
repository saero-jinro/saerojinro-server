package goorm.saerojinro.infra.notification.response;

import goorm.saerojinro.domain.notification.domain.Notification;
import lombok.Builder;

import java.util.List;

@Builder
public record ReceivedNotificationListResponse(
	List<ReceivedNotificationResponse> contents
) {
	public static ReceivedNotificationListResponse from(List<Notification> notifications) {
		return ReceivedNotificationListResponse.builder()
			.contents(notifications.stream()
				.map(ReceivedNotificationResponse::from)
				.toList())
			.build();
	}
}