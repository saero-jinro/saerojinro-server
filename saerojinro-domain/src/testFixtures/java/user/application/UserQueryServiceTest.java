package user.application;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.exception.InvalidPasswordException;
import goorm.saerojinro.domain.user.exception.UserNotFoundException;
import mock.repository.FakeUserRepository;

public class UserQueryServiceTest {
	private UserQueryService userQueryService;

	@BeforeEach
	public void init() {
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		userQueryService = new UserQueryService(fakeUserRepository, bCryptPasswordEncoder);

		fakeUserRepository.save(User.builder()
			.email("email@email.com")
			.password(bCryptPasswordEncoder.encode("password1234!"))
			.name("박민준")
			.role(ADMIN)
			.build()
		);

		UserDetails user = userQueryService.getByEmail("email@email.com");
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
		);
	}

	@Test
	@DisplayName("me는 유저를 현재 로그인한 유저를 조회할 수 있다")
	public void me_Success() {
		// given
		// when
		User result = userQueryService.me();

		// then
		assertEquals("박민준", result.getName());
		assertEquals(ADMIN, result.getRole());
	}

	@Test
	@DisplayName("getByEmail은 유저를 이메일로 조회할 수 있다")
	public void getByEmail_Success() {
		// given
		String email = "email@email.com";

		// when
		User result = userQueryService.getByEmail(email);

		// then
		assertEquals("박민준", result.getName());
		assertEquals(email, result.getEmail());
		assertEquals(ADMIN, result.getRole());
	}

	@Test
	@DisplayName("getByEmail은 해당 이메일을 가진 유저가 존재하지 않으면 UserNotFoundException을 발생한다.")
	public void getByEmail_Failed() {
		// given
		String email = "emai@email.com";

		// when
		assertThatThrownBy(() -> userQueryService.getByEmail(email))
			.isInstanceOf(UserNotFoundException.class);
	}

	@Test
	@DisplayName("login은 유저를 이메일로 조회 후 비밀번호 검증한 뒤 해당 유저를 넘긴다.")
	public void login_Success() {
		// given
		String email = "email@email.com";
		String password = "password1234!";

		// when
		User result = userQueryService.login(email, password);

		// then
		assertEquals("박민준", result.getName());
		assertEquals(email, result.getEmail());
		assertEquals(ADMIN, result.getRole());
	}

	@Test
	@DisplayName("login은 비밀번호 검증에 실패하면 InvalidPasswordException을 발생한다.")
	public void login_Failed() {
		// given
		String email = "email@email.com";
		String password = "password1234";

		// when
		assertThatThrownBy(() -> userQueryService.login(email, password))
			.isInstanceOf(InvalidPasswordException.class);
	}
}
