package goorm.saerojinro.domain.reissue.application;

import org.springframework.stereotype.Service;

import goorm.saerojinro.domain.reissue.domain.RefreshToken;
import goorm.saerojinro.domain.reissue.domain.RefreshTokenRepository;
import goorm.saerojinro.domain.reissue.exception.RefreshTokenNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken getByRefreshToken(String token) {
		return refreshTokenRepository.findByRefreshToken(token)
			.orElseThrow(RefreshTokenNotFoundException::new);
	}

	public void save(Long id, String refreshToken) {
		refreshTokenRepository.save(RefreshToken.of(id, refreshToken));
	}

	public void deleteById(Long id) {
		refreshTokenRepository.deleteById(id);
	}
}
