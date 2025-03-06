package goorm.saerojinro.api.notification.presentation;

import goorm.saerojinro.infra.notification.request.NotificationSendRequest;
import goorm.saerojinro.infra.notification.response.ReceivedNotificationListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "Notification", description = "알림 API")
public interface NotificationController {

	@Operation(summary = "알림 구독 API", description = """
			- Description : 이 API는 사용자가 알림을 구독합니다
			- Assignee : 이신행
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = SseEmitter.class))
	)
	ResponseEntity<SseEmitter> subscribe();

	@Operation(summary = "알림 조회 API", description = """
			- Description : 이 API는 사용자가 받은 알림을 조회합니다
			- Assignee : 이신행
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = ReceivedNotificationListResponse.class)))
	ResponseEntity<ReceivedNotificationListResponse> myNotifications();

	@Operation(summary = "개별 알림 전송 API", description = """
			- Description : 이 API는 특정 사용자에게 알림을 전송합니다
			- Assignee : 이신행
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> sendByUserId(
		@PathVariable Long userId,
		@Parameter(
			description = "전체 알림 request 객체 입니다",
			required = true
		) @Valid @RequestBody NotificationSendRequest sendRequest
	);
}
