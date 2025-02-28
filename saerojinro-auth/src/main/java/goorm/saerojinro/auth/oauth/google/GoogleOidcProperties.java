package goorm.saerojinro.auth.oauth.google;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@ConfigurationProperties(prefix = "oidc.google")
public class GoogleOidcProperties {
	private final String clientId;

	public GoogleOidcProperties(@NotBlank(message = "Google Client ID는 필수 값입니다") String clientId) {
		this.clientId = clientId;
	}
}