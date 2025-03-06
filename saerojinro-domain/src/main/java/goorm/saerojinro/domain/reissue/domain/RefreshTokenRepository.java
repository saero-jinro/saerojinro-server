package goorm.saerojinro.domain.reissue.domain;

import java.util.Optional;

public interface RefreshTokenRepository {
	Optional<RefreshToken> findByRefreshToken(String refreshToken);

	void save(RefreshToken refreshToken);

	void deleteById(Long id);
}
