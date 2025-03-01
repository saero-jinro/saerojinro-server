package goorm.saerojinro.auth.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.auth.presentation.request.EmailLoginRequest;
import goorm.saerojinro.auth.presentation.response.JwtResponse;
import goorm.saerojinro.common.jwt.JwtProvider;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFacade {
	private final UserQueryService userQueryService;
	private final JwtProvider jwtProvider;

	@Transactional(readOnly = true)
	public JwtResponse emailLogin(EmailLoginRequest request) {
		User user = userQueryService.login(request.email(), request.password());

		String accessToken = jwtProvider.generateAccessToken(user.getEmail(), user.getRole());
		String refreshToken = jwtProvider.generateRefreshToken(user.getEmail(), user.getRole());

		return JwtResponse.of(accessToken, refreshToken);
	}
}
