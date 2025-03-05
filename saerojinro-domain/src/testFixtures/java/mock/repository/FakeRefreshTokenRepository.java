package mock.repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import goorm.saerojinro.domain.reissue.domain.RefreshToken;
import goorm.saerojinro.domain.reissue.domain.RefreshTokenRepository;

public class FakeRefreshTokenRepository implements RefreshTokenRepository {
	private final Map<Long, RefreshToken> data = new ConcurrentHashMap<>();

	@Override
	public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
		return data.values().stream()
			.filter(token -> token.getRefreshToken().equals(refreshToken))
			.findFirst();
	}

	@Override
	public void save(RefreshToken refreshToken) {
		data.put(refreshToken.getId(), refreshToken); // userId 기반 저장
	}
}
