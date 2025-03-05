package auth;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import auth.mock.FakeKakaoOidcTokenValidator;
import goorm.saerojinro.auth.api.application.AuthFacade;
import goorm.saerojinro.auth.api.presentation.request.EmailLoginRequest;
import goorm.saerojinro.auth.api.presentation.request.SocialLoginRequest;
import goorm.saerojinro.common.jwt.JwtProperties;
import goorm.saerojinro.common.jwt.JwtProvider;
import goorm.saerojinro.domain.user.application.UserCommandService;
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
			new UserCommandService(fakeUserRepository, bCryptPasswordEncoder),
			new JwtProvider(new JwtProperties("testIssuer", "testSecretKey")),
			new FakeKakaoOidcTokenValidator()
		);

		fakeUserRepository.save(User.builder()
			.email("email@email.com")
			.password(bCryptPasswordEncoder.encode("password1234!"))
			.name("박민준")
			.role(ADMIN)
			.build()
		);

		fakeUserRepository.save(User.createKakaoUser(
			"kakao_123456", "test", "test@kakao.com", "http://example.com/test.png"
		));
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

	@Test
	@DisplayName("kakaoSocailLogin은 kakao 로그인 시 토큰을 발급한다.")
	public void kakaoSocailLogin_Success() {
		// given
		SocialLoginRequest request = new SocialLoginRequest("test");

		// when
		assertThatCode(() -> authFacade.kakaoSocialLogin(request)
		).doesNotThrowAnyException();
	}
}
