package goorm.saerojinro.auth.api.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goorm.saerojinro.auth.api.application.AuthFacade;
import goorm.saerojinro.auth.api.presentation.request.AdminLoginRequest;
import goorm.saerojinro.auth.api.presentation.request.OidcLoginRequest;
import goorm.saerojinro.auth.api.presentation.response.JwtResponse;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthControllerImpl implements AuthController {
	private final AuthFacade authFacade;

	@Override
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> adminLogin(
		@Valid @RequestBody AdminLoginRequest request
	) {
		JwtResponse response = authFacade.adminLogin(request);
		return ResponseEntity.ok(response);
	}

	@Override
	@PostMapping("/kakao/login")
	public ResponseEntity<JwtResponse> kakaoLogin(
		@Valid @RequestBody OidcLoginRequest request
	) {
		JwtResponse response = authFacade.kakaoLogin(request);
		return ResponseEntity.ok(response);
	}

	@Override
	@PostMapping("/google/login")
	public ResponseEntity<JwtResponse> googleLogin(
		@Valid @RequestBody OidcLoginRequest request
	) {
		JwtResponse response = authFacade.googleLogin(request);
		return ResponseEntity.ok(response);
	}
}
