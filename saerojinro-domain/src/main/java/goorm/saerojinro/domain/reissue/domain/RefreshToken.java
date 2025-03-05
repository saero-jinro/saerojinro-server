package goorm.saerojinro.domain.reissue.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

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

	@Indexed
	private String refreshToken;

	public static RefreshToken of(Long id, String refreshToken) {
		return RefreshToken.builder()
			.id(id)
			.refreshToken(refreshToken)
			.build();
	}
}
