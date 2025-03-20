package goorm.saerojinro.api.notification.presentation.response;

import goorm.saerojinro.domain.notification.domain.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record ReceivedNotificationResponse(
	@Schema(description = "제목", example = "[전체 공지] 불났어요", requiredMode = REQUIRED)
	String title,

	@Schema(description = "내용", example = "다들 돔황챠", requiredMode = REQUIRED)
	String contents,

	@Schema(description = "전송시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
	LocalDateTime createdAt
) {
	public static ReceivedNotificationResponse from(Notification notification) {
		return ReceivedNotificationResponse.builder()
			.title(notification.getTitle())
			.contents(notification.getContents())
			.createdAt(notification.getCreatedAt())
			.build();
	}
}
