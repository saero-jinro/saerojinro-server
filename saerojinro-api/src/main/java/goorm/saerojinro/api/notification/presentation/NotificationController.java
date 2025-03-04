package goorm.saerojinro.api.notification.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "Notification", description = "알림 API")
public interface NotificationController {

	@Operation(summary = "알림 구독 API", description = """
			- Description : 이 API는 사용자가 예약한 강의 찾아 알림을 구독합니다
			- Assignee : 이신행
		""")
	@ApiResponses()
	ResponseEntity<SseEmitter> subscribe();
}
