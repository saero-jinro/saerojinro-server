package goorm.saerojinro.domain.reissue.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import goorm.saerojinro.domain.user.exception.UserNotAuthenticatedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24)
@Builder
@AllArgsConstructor
public class RefreshToken {
	@Id
	private Long id;

	private String refreshToken;

	public static RefreshToken of(Long id, String refreshToken) {
		return RefreshToken.builder()
			.id(id)
			.refreshToken(refreshToken)
			.build();
	}

	public boolean validateRefreshToken(String refreshToken) {
		return Objects.equals(this.refreshToken, refreshToken);
	}
}
