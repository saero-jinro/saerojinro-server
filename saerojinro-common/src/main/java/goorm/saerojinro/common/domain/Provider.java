package goorm.saerojinro.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Provider {
	KAKAO("https://kauth.kakao.com/.well-known/jwks.json", "https://kauth.kakao.com"),
	GOOGLE("https://www.googleapis.com/oauth2/v3/certs", "https://accounts.google.com"),
	;

	private final String jwkSetUri;
	private final String issuer;
}
