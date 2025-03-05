package goorm.saerojinro.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import goorm.saerojinro.auth.presentation.request.EmailLoginRequest;
import goorm.saerojinro.auth.presentation.request.SocialLoginRequest;
import goorm.saerojinro.auth.presentation.response.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Auth", description = "로그인 API")
public interface AuthController {
	@Operation(summary = "로그인 API", description = """
			- Description : 이 API는 이메일 기반 로그인을 처리합니다.
			- Assignee : 박민준
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = JwtResponse.class)))
	ResponseEntity<JwtResponse> emailLogin(
		@Parameter(
			description = "이메일 로그인 request 객체 입니다.",
			required = true
		) @Valid @RequestBody EmailLoginRequest request
	);

	@Operation(summary = "카카오 소셜 로그인 API", description = """
			- Description : 이 API는 카카오 소셜 로그인을 처리합니다.
			- Assignee : 박민준
		""")
	@ApiResponse(
		responseCode = "200",
		content = @Content(schema = @Schema(implementation = JwtResponse.class)))
	ResponseEntity<JwtResponse> kakaoSocialLogin(
		@Parameter(
			description = "이메일 로그인 request 객체 입니다.",
			required = true
		) @Valid @RequestBody SocialLoginRequest request
	);
}
