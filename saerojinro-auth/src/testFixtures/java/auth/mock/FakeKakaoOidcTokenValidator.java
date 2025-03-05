package auth.mock;

import goorm.saerojinro.auth.social.kakao.KakaoOidcTokenValidator;
import goorm.saerojinro.auth.social.kakao.KakaoOidcProperties;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class FakeKakaoOidcTokenValidator extends KakaoOidcTokenValidator {

	public FakeKakaoOidcTokenValidator() {
		super(new KakaoOidcProperties("kakaoClientId"));
	}

	@Override
	public OidcIdToken validateAndDecodeIdToken(String idToken) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("sub", "kakao_123456");
		claims.put("nickname", "test");
		claims.put("email", "test@kakao.com");
		claims.put("picture", "http://example.com/test.png");

		return new OidcIdToken(
			idToken,
			Instant.now(),
			Instant.now().plusSeconds(3600),
			claims
		);
	}
}
