package goorm.saerojinro.auth.oauth.kakao;

import static goorm.saerojinro.common.auth.domain.Provider.KAKAO;

import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

import goorm.saerojinro.auth.oauth.OidcService;
import goorm.saerojinro.common.auth.domain.Provider;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
@Service
public class KakaoOidcService extends OidcService {
	private final KakaoOidcProperties properties;

	public KakaoOidcService(UserRepository userRepository, KakaoOidcProperties properties) {
		super(userRepository);
		this.properties = properties;
	}

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

	@Override
	protected User createNewUser(String identifier, OidcUserInfo oidcUserInfo) {
		String name = oidcUserInfo.getNickName();

		return User.kakaoOidcCreate(identifier, KAKAO, name);
	}
}

