package goorm.saerojinro.common.auth.jwt.response;

import lombok.Builder;

@Builder
public record JwtResponse(
	String accessToken,
	String refreshToken
) {
	public static JwtResponse of(String accessToken, String refreshToken) {
		return JwtResponse.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}
}
