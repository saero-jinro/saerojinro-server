package goorm.saerojinro.infra.repository.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import goorm.saerojinro.domain.reissue.domain.RefreshToken;
import goorm.saerojinro.domain.reissue.domain.RefreshTokenRepository;
import goorm.saerojinro.infra.repository.redis.RedisRefreshTokenRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
	private final RedisRefreshTokenRepository redisRefreshTokenRepository;

	@Override
	public Optional<RefreshToken> findById(Long id) {
		return redisRefreshTokenRepository.findById(id);
	}

	@Override
	public void save(RefreshToken refreshToken) {
		redisRefreshTokenRepository.save(refreshToken);
	}
}
