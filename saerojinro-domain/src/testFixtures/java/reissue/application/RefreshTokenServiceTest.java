package reissue.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import goorm.saerojinro.common.domain.reissue.application.RefreshTokenService;
import goorm.saerojinro.common.domain.reissue.domain.RefreshToken;
import mock.repository.FakeRefreshTokenRepository;

public class RefreshTokenServiceTest {
	private RefreshTokenService refreshTokenService;
	private final String REFRESH_TOKEN = "REFRESH_TOKEN";
	private final Long ID = 1L;

	@BeforeEach
	public void init() {
		FakeRefreshTokenRepository fakeRefreshTokenRepository = new FakeRefreshTokenRepository();
		refreshTokenService = new RefreshTokenService(fakeRefreshTokenRepository);

		fakeRefreshTokenRepository.save(RefreshToken.of(ID, REFRESH_TOKEN));
	}

	@Test
	@DisplayName("getByRefreshToken은 refreshToken을 조회하여 RefreshToken 객체를 가져온다.")
	public void getByRefreshToken_Success() {
		// when
		RefreshToken response = refreshTokenService.getByRefreshToken(REFRESH_TOKEN);

		// then
		assertEquals(ID, response.getId());
		assertEquals(REFRESH_TOKEN, response.getRefreshToken());
	}

	@Test
	@DisplayName("save는 RefreshToken을 저장한다.")
	public void save_Success() {
		// given
		Long id = 2L;
		String refreshToken = "REFRESH_TOKEN2";

		// when
		refreshTokenService.save(id, refreshToken);

		// then
		RefreshToken response = refreshTokenService.getByRefreshToken(refreshToken);
		assertEquals(id, response.getId());
		assertEquals(refreshToken, response.getRefreshToken());
	}
}
