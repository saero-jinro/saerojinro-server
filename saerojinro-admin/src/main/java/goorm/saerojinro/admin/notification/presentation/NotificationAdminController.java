package goorm.saerojinro.admin.notification.presentation;

import goorm.saerojinro.admin.notification.presentation.request.NotificationSendRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Notification", description = "운영자 알림 API")
public interface NotificationAdminController {

	@Operation(summary = "전체 알림 전송 API", description = """
			- Description : 이 API는 모든 사용자에게 알림을 전송합니다
			- Assignee : 이신행
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> sendAll(
		@Parameter(
			description = "전체 알림 request 객체 입니다",
			required = true
		) @Valid @RequestBody NotificationSendRequest sendRequest
	);

	@Operation(summary = "강의 기준 알림 전송 API", description = """
			- Description : 이 API는 강의를 예약한 사용자에게 알림을 전송합니다
			- Assignee : 이신행
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> sendByLecture(
		@PathVariable Long lectureId,
		@Parameter(
			description = "전체 알림 request 객체 입니다",
			required = true
		) @Valid @RequestBody NotificationSendRequest sendRequest
	);
}
