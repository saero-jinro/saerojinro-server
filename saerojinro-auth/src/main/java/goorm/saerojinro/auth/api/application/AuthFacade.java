package goorm.saerojinro.auth.api.application;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.auth.api.presentation.request.EmailLoginRequest;
import goorm.saerojinro.auth.api.presentation.request.SocialLoginRequest;
import goorm.saerojinro.auth.api.presentation.response.JwtResponse;
import goorm.saerojinro.auth.social.dto.SocialUserProfile;
import goorm.saerojinro.auth.social.kakao.KakaoOidcTokenValidator;
import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.jwt.JwtProvider;
import goorm.saerojinro.domain.reissue.application.RefreshTokenService;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthFacade {
	private final UserQueryService userQueryService;
	private final UserCommandService userCommandService;
	private final JwtProvider jwtProvider;
	private final KakaoOidcTokenValidator kakaoOidcTokenValidator;
	private final RefreshTokenService refreshTokenService;

	@Transactional(readOnly = true)
	public JwtResponse emailLogin(EmailLoginRequest request) {
		User user = userQueryService.login(request.email(), request.password());

		String accessToken = jwtProvider.generateAccessToken(user.getEmail(), user.getRole());
		String refreshToken = jwtProvider.generateRefreshToken(user.getEmail(), user.getRole());

		refreshTokenService.save(user.getId(), refreshToken);
		return JwtResponse.of(accessToken, refreshToken);
	}

	@Transactional
	public JwtResponse kakaoSocialLogin(SocialLoginRequest request) {
		OidcIdToken oidcIdToken = kakaoOidcTokenValidator.validateAndDecodeIdToken(request.idToken());

		SocialUserProfile socialUserProfile = SocialUserProfile.from(oidcIdToken);
		String email = socialUserProfile.email();

		User user = userCommandService.kakaoSocialLogin(
			socialUserProfile.identifier(),
			socialUserProfile.name(),
			email,
			socialUserProfile.profileImage()
		);

		BaseRole role = user.getRole();
		String accessToken = jwtProvider.generateAccessToken(email, role);
		String refreshToken = jwtProvider.generateRefreshToken(email, role);

		refreshTokenService.save(user.getId(), refreshToken);
		return JwtResponse.of(accessToken, refreshToken);
	}

	public JwtResponse reissue() {

	}
}
