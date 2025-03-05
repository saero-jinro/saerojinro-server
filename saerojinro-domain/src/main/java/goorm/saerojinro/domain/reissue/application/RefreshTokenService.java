package goorm.saerojinro.domain.reissue.application;

import org.springframework.stereotype.Service;

import goorm.saerojinro.domain.reissue.domain.RefreshToken;
import goorm.saerojinro.domain.reissue.domain.RefreshTokenRepository;
import goorm.saerojinro.domain.reissue.exception.RefreshTokenMismatchException;
import goorm.saerojinro.domain.reissue.exception.RefreshTokenNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
	private final RefreshTokenRepository refreshTokenRepository;

	public void validate(Long id, String token) {
		RefreshToken refreshToken = refreshTokenRepository.findById(id)
			.orElseThrow(RefreshTokenNotFoundException::new);

		if(!refreshToken.validateRefreshToken(token)){
			throw new RefreshTokenMismatchException();
		}
	}

	public void save(Long id, String refreshToken) {
		refreshTokenRepository.save(RefreshToken.of(id, refreshToken));
	}
}
