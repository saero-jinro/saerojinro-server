package reissue;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import goorm.saerojinro.domain.reissue.domain.RefreshToken;

public class RefreshTokenDomainTest {
	@Test
	@DisplayName("of는 id와 refreshToken을 받아 RefreshToken을 생성한다")
	public void of_Success() {
		// given
		Long id = 1L;
		String refreshToken = "refreshToken";

		// when
		RefreshToken response = RefreshToken.of(id, refreshToken);

		// then
		assertEquals(id, response.getId());
		assertEquals(refreshToken, response.getRefreshToken());
	}
}
