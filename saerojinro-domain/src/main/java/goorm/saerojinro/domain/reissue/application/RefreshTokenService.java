package goorm.saerojinro.domain.reissue.application;

import org.springframework.stereotype.Service;

import goorm.saerojinro.domain.reissue.domain.RefreshToken;
import goorm.saerojinro.domain.reissue.domain.RefreshTokenRepository;
import goorm.saerojinro.domain.reissue.exception.TokenNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken getById(Long id) {
		return refreshTokenRepository.findById(id)
			.orElseThrow(TokenNotFoundException::new);
	}

	public void save(Long id, String refreshToken) {
		refreshTokenRepository.save(RefreshToken.of(id, refreshToken));
	}
}
