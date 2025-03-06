package goorm.saerojinro.common.domain.blacklist.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@RedisHash(value = "blackList", timeToLive = 60 * 60)
@Builder
@AllArgsConstructor
public class BlackList {
	@Id
	private String accessToken;

	public static BlackList of(String accessToken) {
		return BlackList.builder()
			.accessToken(accessToken)
			.build();
	}
}
