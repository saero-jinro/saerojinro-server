package goorm.saerojinro.auth.social.kakao;

import static goorm.saerojinro.common.domain.Provider.KAKAO;

import org.springframework.stereotype.Service;

import goorm.saerojinro.auth.social.OidcTokenValidator;
import goorm.saerojinro.common.domain.Provider;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KakaoOidcTokenValidator extends OidcTokenValidator {
	private final KakaoOidcProperties properties;

	@Override
	protected String getIssuer() {
		return getProvider().getIssuer();
	}

	@Override
	protected String getJwkSetUri() {
		return getProvider().getJwkSetUri();
	}

	@Override
	protected String getClientId() {
		return properties.getClientId();
	}

	@Override
	protected Provider getProvider() {
		return KAKAO;
	}
}

