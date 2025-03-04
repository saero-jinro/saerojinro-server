package auth;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import goorm.saerojinro.auth.application.AuthFacade;
import goorm.saerojinro.auth.presentation.request.EmailLoginRequest;
import goorm.saerojinro.auth.social.kakao.KakaoOidcProperties;
import goorm.saerojinro.auth.social.kakao.KakaoOidcService;
import goorm.saerojinro.common.jwt.JwtProperties;
import goorm.saerojinro.common.jwt.JwtProvider;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeUserRepository;

public class AuthFacadeTest {
	private AuthFacade authFacade;

	@BeforeEach
	public void init() {
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		authFacade = new AuthFacade(
			new UserQueryService(fakeUserRepository, bCryptPasswordEncoder),
			new JwtProvider(new JwtProperties("testIssuer", "testSecretKey")),
			new KakaoOidcService(fakeUserRepository, new KakaoOidcProperties("kakaoClientId"))
		);

		fakeUserRepository.save(User.builder()
			.email("email@email.com")
			.password(bCryptPasswordEncoder.encode("password1234!"))
			.name("박민준")
			.role(ADMIN)
			.build()
		);
	}

	@Test
	@DisplayName("login은 토큰을 발급할 수 있다")
	public void login_Success() {
		// given
		String email = "email@email.com";
		String password = "password1234!";

		// when
		// then
		assertThatCode(() -> authFacade.emailLogin(
			EmailLoginRequest.builder()
				.email(email)
				.password(password)
				.build()
			)
		).doesNotThrowAnyException();
	}
}
