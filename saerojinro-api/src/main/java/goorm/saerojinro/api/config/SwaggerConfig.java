package goorm.saerojinro.api.config;

import static java.lang.String.format;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
	@Value("${docs.admin-docs-url}")
	private String adminDocsUrl;

	@Value("${docs.auth-docs-url}")
	private String authDocsUrl;

	private final Environment environment;

	private static final Map<String, String> PROFILE_SERVER_URL_MAP = Map.of(
		"local", "http://localhost:8080"
	);

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(apiInfo())
			.addSecurityItem(securityRequirement())
			.components(components())
			.servers(initializeServers());
	}

	private List<Server> initializeServers() {
		String activeProfile = environment.getActiveProfiles()[0];
		String serverUrl = PROFILE_SERVER_URL_MAP.getOrDefault(activeProfile, "http://localhost:8080");
		return List.of(new Server().url(serverUrl).description("SAERO-JINRO API (" + activeProfile + ")"));
	}

	private SecurityRequirement securityRequirement() {
		return new SecurityRequirement().addList("bearer");
	}

	private Info apiInfo() {
		return new Info()
			.title("SAERO-JINRO API")
			.description(getDescription());
	}

	private Components components() {
		return new Components().addSecuritySchemes("bearer", securityScheme());
	}

	private SecurityScheme securityScheme() {
		return new SecurityScheme()
			.name("bearer")
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT");
	}

	private String getDescription() {
		return format("""
				처음처럼 참가자 페이지, SAERO-JINRO API 입니다.
				
				로그인 API를 통해 액세스 토큰을 발급 받고 헤더에 값을 넣어주세요.
				
				별다른 절차 없이 API를 사용하실 수 있습니다.
				
				<ul>
					<li>관리자 API 문서: <a href="%s" target="_blank">%s</a></li><br>
					<li>인증/인가 API 문서: <a href="%s" target="_blank">%s</a></li>
            	</ul>
				""",
			adminDocsUrl, adminDocsUrl,
			authDocsUrl, authDocsUrl);
	}
}
