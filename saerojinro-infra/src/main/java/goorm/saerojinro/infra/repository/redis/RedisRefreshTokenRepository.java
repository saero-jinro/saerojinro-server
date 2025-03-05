package goorm.saerojinro.infra.repository.redis;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import goorm.saerojinro.domain.reissue.domain.RefreshToken;

public interface RedisRefreshTokenRepository extends CrudRepository<RefreshToken, String> {
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
