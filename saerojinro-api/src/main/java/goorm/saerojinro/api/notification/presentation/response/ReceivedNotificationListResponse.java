package goorm.saerojinro.api.notification.presentation.response;

import goorm.saerojinro.domain.notification.domain.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record ReceivedNotificationListResponse(
	@Schema(description = "받은 알림 리스트",
		example = "[{"
			+ "\"title\": \"[전체 공지] 불났어요\", "
			+ "\"contents\": \"다들 돔황챠\"}]",
		requiredMode = REQUIRED)
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