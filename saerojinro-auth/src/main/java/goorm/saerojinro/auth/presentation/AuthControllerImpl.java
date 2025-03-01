package goorm.saerojinro.auth.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goorm.saerojinro.auth.application.AuthFacade;
import goorm.saerojinro.auth.presentation.request.EmailLoginRequest;
import goorm.saerojinro.auth.presentation.response.JwtResponse;
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
}
