package goorm.saerojinro.auth.oauth.kakao;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "oidc.kakao")
public class KakaoOidcProperties {
	private final String clientId;

	public KakaoOidcProperties(@NotBlank(message = "Kakao Client ID는 필수 값입니다") String clientId) {
		this.clientId = clientId;
	}
}