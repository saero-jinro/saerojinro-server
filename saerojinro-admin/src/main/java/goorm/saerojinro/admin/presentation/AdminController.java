package goorm.saerojinro.admin.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import goorm.saerojinro.admin.presentation.request.AdminCreateRequest;
import goorm.saerojinro.admin.presentation.response.AdminPersistResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User", description = "회원 API")
public interface AdminController {
	@Operation(summary = "관리자 등록 API", description = """
			- Description : 이 API는 새로운 관리자를 등록합니다.
			- Assignee : 박민준
		""")
	@ApiResponse(
		responseCode = "201",
		content = @Content(schema = @Schema(implementation = AdminPersistResponse.class)))
	ResponseEntity<AdminPersistResponse> createAdmin(
		@Parameter(
			description = "관리자 등록 request 객체 입니다.",
			required = true
		) @Valid @RequestBody AdminCreateRequest request
	);
}
