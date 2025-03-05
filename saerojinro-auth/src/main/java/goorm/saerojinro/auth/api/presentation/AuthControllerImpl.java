package goorm.saerojinro.auth.api.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goorm.saerojinro.auth.api.application.AuthFacade;
import goorm.saerojinro.auth.api.presentation.request.SocialLoginRequest;
import goorm.saerojinro.auth.api.presentation.response.JwtResponse;
import goorm.saerojinro.auth.api.presentation.request.EmailLoginRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
	private final AuthFacade authFacade;

	@Override
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> emailLogin(EmailLoginRequest request) {
		JwtResponse response = authFacade.emailLogin(request);
		return ResponseEntity.ok(response);
	}

	@Override
	@PostMapping("/kakao/login")
	public ResponseEntity<JwtResponse> kakaoSocialLogin(SocialLoginRequest request) {
		JwtResponse response = authFacade.kakaoSocialLogin(request);
		return ResponseEntity.ok(response);
	}
}
