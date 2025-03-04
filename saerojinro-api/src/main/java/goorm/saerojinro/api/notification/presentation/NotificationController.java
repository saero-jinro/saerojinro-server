package goorm.saerojinro.api.notification.presentation;

import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "Notification", description = "알림 API")
public interface NotificationController {

	@Operation(summary = "알림 구독 API", description = """
			- Description : 이 API는 사용자가 알림을 구독합니다
			- Assignee : 이신행
		""")
	@ApiResponses()
	ResponseEntity<SseEmitter> subscribe();

	@Operation(summary = "알림 조회 API", description = """
			- Description : 이 API는 사용자가 받은 알림을 조회합니다
			- Assignee : 이신행
		""")
	@ApiResponses()
	ResponseEntity<ReceivedNotificationListResponse> myNotifications();

	// TODO 추후 Admin 모듈로 분리 예정
	@Operation(summary = "전체 알림 전송 API", description = """
			- Description : 이 API는 모든 사용자에게 알림을 전송합니다
			- Assignee : 이신행
		""")
	@ApiResponses()
	ResponseEntity<Void> sendAll();

	@Operation(summary = "강의 기준 알림 전송 API", description = """
			- Description : 이 API는 강의를 예약한 사용자에게 알림을 전송합니다
			- Assignee : 이신행
		""")
	@ApiResponses()
	ResponseEntity<Void> sendByLecture(
		@PathVariable Long lectureId
	);

	@Operation(summary = "개별 알림 전송 API", description = """
			- Description : 이 API는 특정 사용자에게 알림을 전송합니다
			- Assignee : 이신행
		""")
	@ApiResponses()
	ResponseEntity<Void> sendByUserId(
		@PathVariable Long userId
	);
}
