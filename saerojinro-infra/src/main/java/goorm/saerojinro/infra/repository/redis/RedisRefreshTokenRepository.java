package goorm.saerojinro.infra.repository.redis;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import goorm.saerojinro.common.domain.reissue.domain.RefreshToken;

public interface RedisRefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByRefreshToken(String refreshToken);

	void deleteById(Long id);
}
