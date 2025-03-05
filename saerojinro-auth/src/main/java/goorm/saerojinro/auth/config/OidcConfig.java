package goorm.saerojinro.auth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import goorm.saerojinro.auth.social.google.GoogleOidcProperties;
import goorm.saerojinro.auth.social.kakao.KakaoOidcProperties;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableConfigurationProperties({
	GoogleOidcProperties.class,
	KakaoOidcProperties.class
})
@RequiredArgsConstructor
public class OidcConfig {
}
