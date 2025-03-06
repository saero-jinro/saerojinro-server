package goorm.saerojinro.auth.api.application;

import java.util.UUID;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.auth.api.presentation.request.EmailLoginRequest;
import goorm.saerojinro.auth.api.presentation.request.ReissueRequest;
import goorm.saerojinro.auth.api.presentation.request.SocialLoginRequest;
import goorm.saerojinro.auth.api.presentation.response.JwtResponse;
import goorm.saerojinro.auth.social.dto.SocialUserProfile;
import goorm.saerojinro.auth.social.kakao.KakaoOidcTokenValidator;
import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.jwt.JwtProvider;
import goorm.saerojinro.domain.reissue.application.RefreshTokenService;
import goorm.saerojinro.domain.reissue.domain.RefreshToken;
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

		return createToken(user.getId(), user.getEmail(), user.getRole());
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

		return createToken(user.getId(), user.getEmail(), user.getRole());
	}

	@Transactional(readOnly = true)
	public JwtResponse reissue(ReissueRequest request) {
		RefreshToken refreshToken = refreshTokenService.getByRefreshToken(request.refreshToken());

		User user = userQueryService.getById(refreshToken.getId());

		return createToken(user.getId(), user.getEmail(), user.getRole());
	}

	private JwtResponse createToken(Long id, String email, BaseRole role) {
		String refreshToken = UUID.randomUUID().toString();
		String accessToken = jwtProvider.generateAccessToken(email, role);
		refreshTokenService.save(id, refreshToken);
		return JwtResponse.of(accessToken, refreshToken);
	}
}
