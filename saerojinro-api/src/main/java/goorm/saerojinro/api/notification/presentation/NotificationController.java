package goorm.saerojinro.api.notification.presentation;

import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "Notification", description = "알림 API")
public interface NotificationController {

	@Operation(summary = "알림 구독 API", description = """
			- Description : 이 API는 사용자가 알림을 구독합니다
			- Assignee : 이신행
		""")
	@ApiResponse(responseCode = "200")
	ResponseEntity<SseEmitter> subscribe();

	@Operation(summary = "알림 조회 API", description = """
			- Description : 이 API는 사용자가 받은 알림을 조회합니다
			- Assignee : 이신행
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = ReceivedNotificationListResponse.class)))
	ResponseEntity<ReceivedNotificationListResponse> myNotifications();

}
