package goorm.saerojinro.infra.repository.impl;

import org.springframework.stereotype.Repository;

import goorm.saerojinro.domain.blacklist.domain.BlackList;
import goorm.saerojinro.domain.blacklist.domain.BlackListRepository;
import goorm.saerojinro.infra.repository.redis.RedisBlackListRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BlackListRepositoryImpl implements BlackListRepository {
	private final RedisBlackListRepository redisBlackListRepository;

	@Override
	public void save(BlackList blackList) {
		redisBlackListRepository.save(blackList);
	}
}
