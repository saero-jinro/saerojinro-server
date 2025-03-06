package goorm.saerojinro.infra.repository.impl;

import org.springframework.stereotype.Repository;

import goorm.saerojinro.common.domain.blacklist.domain.BlackListRepository;
import goorm.saerojinro.infra.repository.redis.RedisBlackListRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlackListRepositoryImpl implements BlackListRepository {
	private final RedisBlackListRepository redisBlackListRepository;

	@Override
	public void add(String accessToken, Long ttlInSecond) {
		redisBlackListRepository.add(accessToken, ttlInSecond);
	}

	@Override
	public boolean isBlackListed(String accessToken) {
		return redisBlackListRepository.isBlackListed(accessToken);
	}
}
