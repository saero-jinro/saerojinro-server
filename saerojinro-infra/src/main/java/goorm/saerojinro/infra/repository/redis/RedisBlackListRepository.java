package goorm.saerojinro.infra.repository.redis;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisBlackListRepository {
	private final RedisTemplate<String, String> redisTemplate;
	private static final String KEY_PREFIX = "blackList:";

	public void add(String accessToken, Long ttlInSeconds) {
		redisTemplate.opsForValue().set(
			KEY_PREFIX + accessToken,
			"BLACKLISTED",
			ttlInSeconds,
			SECONDS
		);
	}

	public boolean isBlackListed(String accessToken) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(KEY_PREFIX + accessToken));
	}
}
