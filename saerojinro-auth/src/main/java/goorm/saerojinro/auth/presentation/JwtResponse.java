package goorm.saerojinro.auth.presentation;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record JwtResponse(
	@Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTYzNzQwNjQwMH0.7J", requiredMode = REQUIRED)
	String accessToken,

	@Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsILJdhDYTYzNzQwNjQwMH0.7J", requiredMode = REQUIRED)
	String refreshToken
) {
	public static JwtResponse of(String accessToken, String refreshToken) {
		return JwtResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}