package goorm.saerojinro.auth.api.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.auth.api.presentation.request.AdminLoginRequest;
import goorm.saerojinro.auth.api.presentation.request.OidcLoginRequest;
import goorm.saerojinro.auth.api.presentation.response.JwtResponse;
import goorm.saerojinro.auth.oauth.google.GoogleOidcService;
import goorm.saerojinro.auth.oauth.kakao.KakaoOidcService;
import goorm.saerojinro.common.auth.jwt.application.JwtProvider;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFacade {
	private final KakaoOidcService kakaoOidcService;
	private final GoogleOidcService googleOidcService;
	private final JwtProvider jwtProvider;
	private final UserQueryService userQueryService;
	private final UserCommandService userCommandService;

	@Transactional
	public JwtResponse adminLogin(AdminLoginRequest request) {
		User user = userQueryService.getByEmail(request.email());
		userCommandService.login(user, request.password());

		return getToken(user);
	}

	@Transactional
	public JwtResponse kakaoLogin(OidcLoginRequest idToken) {
		User user = kakaoOidcService.processLogin(idToken.idToken());
		return getToken(user);
	}

	@Transactional
	public JwtResponse googleLogin(OidcLoginRequest idToken) {
		User user = googleOidcService.processLogin(idToken.idToken());
		return getToken(user);
	}

	private JwtResponse getToken(User user) {
		String refreshToken = jwtProvider.generateRefreshToken(user.getId(), user.getRole());
		String accessToken = jwtProvider.generateAccessToken(user.getId(), user.getRole());

		return JwtResponse.of(accessToken, refreshToken);
	}
}
