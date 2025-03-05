package goorm.saerojinro.domain.reissue.domain;

import java.util.Optional;

public interface RefreshTokenRepository {
	Optional<RefreshToken> findById(Long id);

	void save(RefreshToken refreshToken);
}
