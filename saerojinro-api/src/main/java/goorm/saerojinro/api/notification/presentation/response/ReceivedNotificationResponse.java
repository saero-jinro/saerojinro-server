package goorm.saerojinro.api.notification.presentation.response;

import goorm.saerojinro.domain.notification.domain.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record ReceivedNotificationResponse(
	@Schema(description = "제목", example = "[전체 공지] 불났어요", requiredMode = REQUIRED)
	String title,

	@Schema(description = "내용", example = "다들 돔황챠", requiredMode = REQUIRED)
	String contents
) {
	public static ReceivedNotificationResponse from(Notification notification) {
		return ReceivedNotificationResponse.builder()
			.title(notification.getTitle())
			.contents(notification.getContents())
			.build();
	}
}
