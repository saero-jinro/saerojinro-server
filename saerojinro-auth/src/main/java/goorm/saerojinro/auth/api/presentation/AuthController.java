package goorm.saerojinro.auth.api.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import goorm.saerojinro.auth.api.presentation.request.AdminLoginRequest;
import goorm.saerojinro.auth.api.presentation.request.OidcLoginRequest;
import goorm.saerojinro.auth.api.presentation.response.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "로그인 API")
public interface AuthController {
	@Operation(summary = "관리자 로그인 API", description = """
			- Description : 이 API는 jwt 토큰 기반 로그인을 처리합니다.
			- Assignee : 박민준
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = JwtResponse.class)))
	ResponseEntity<JwtResponse> adminLogin(
		@Parameter(
			description = "관리자 로그인 request 객체 입니다.",
			required = true
		) @Valid @RequestBody AdminLoginRequest request
	);

	@Operation(summary = "카카오 소셜 로그인 API", description = """
			- Description : 이 API는 카카오 소셜 로그인을 처리합니다.
			- Assignee : 박민준
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = JwtResponse.class)))
	ResponseEntity<JwtResponse> kakaoLogin(
		@Parameter(
			description = "카카오 소셜 로그인 request 객체 입니다.",
			required = true
		) @Valid @RequestBody OidcLoginRequest request
	);

	@Operation(summary = "구글 소셜 로그인 API", description = """
			- Description : 이 API는 구글 소셜 로그인을 처리합니다.
			- Assignee : 박민준
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = JwtResponse.class)))
	ResponseEntity<JwtResponse> googleLogin(
		@Parameter(
			description = "구글 소셜 로그인 request 객체 입니다.",
			required = true
		) @Valid @RequestBody OidcLoginRequest request
	);
}
