package goorm.saerojinro.api.user.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import goorm.saerojinro.api.user.presentation.request.UserUpdateRequest;
import goorm.saerojinro.api.user.presentation.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "User", description = "유저 API")
public interface UserController {
	@Operation(summary = "회원 정보 수정 API", description = """
			- Description : 이 API는 회원의 이름, 이메일, 관심사 정보를 수정 합니다.
			- Assignee : 박민준
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> update(
		@Parameter(
			description = "회원 정보 수정 request 객체 입니다.",
			required = true
		) @Valid @RequestBody UserUpdateRequest request
	);

	@Operation(summary = "회원 탈퇴 API", description = """
			- Description : 이 API는 회원정보를 삭제합니다.
			- Assignee : 박민준
		""")
	@ApiResponse(responseCode = "204")
	ResponseEntity<Void> delete();

	@Operation(summary = "마이페이지 API", description = """
			- Description : 이 API는 현재 로그인 된 유저의 정보를 조회합니다.
			- Assignee : 박민준
		""")
	@ApiResponse(
		responseCode = "204",
		content = @Content(schema = @Schema(implementation = UserInfoResponse.class))
	)
	ResponseEntity<UserInfoResponse> getUserInfo();
}
