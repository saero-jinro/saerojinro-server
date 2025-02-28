package user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeUserRepository;

public class UserCommandServiceTest {
	private UserCommandService userCommandService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private User user;

	@BeforeEach
	public void init() {
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
		userCommandService = new UserCommandService(
			fakeUserRepository
			,new BCryptPasswordEncoder()
		);
	}

	@Test
	@DisplayName("createAdmin은 관리자를 생성할 수 있다.")
	public void createAdmin_Success() {
		// given
		String email = "admin@gmail.com";
		String password = "password";
		String name = "admin";

		// when
		User user = userCommandService.createAdmin(email, password, name);

		// then
		assertEquals(email, user.getEmail());
		assertTrue(bCryptPasswordEncoder.matches(password, user.getPassword()));
		assertEquals(name, user.getName());
	}
}
