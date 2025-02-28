package user;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;
import static goorm.saerojinro.common.domain.BaseRole.SPEAKER;
import static goorm.saerojinro.common.domain.Interest.BACKEND;
import static goorm.saerojinro.common.domain.Provider.GOOGLE;
import static goorm.saerojinro.common.domain.Provider.KAKAO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import goorm.saerojinro.common.domain.BaseRole;
import goorm.saerojinro.common.domain.Interest;
import goorm.saerojinro.domain.user.domain.User;

public class UserDomainTest {
	private User user;
	private static final String PASSWORD = "$2a$10$ViIAGtB9Y/9cE//3WY6i4e6RQVHbJhQQDWshsFlElNnyz88.8EOu2";
	private static final String NAME = "홍길동";
	private static final String EMAIL = "valid@kgu.ac.kr";
	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	@BeforeEach
	public void init() {
		user = User.createAdmin(EMAIL, PASSWORD, NAME);
	}

	@Test
	@DisplayName("createAdmin은 ADMIN USER를 생성할 수 있다.")
	public void createAdmin_Success() {
		// when
		// then
		assertNotNull(user);
		assertEquals(EMAIL, user.getEmail());
		assertEquals(PASSWORD, user.getPassword());
		assertEquals(NAME, user.getName());
		assertEquals(ADMIN, user.getRole());
	}

	@Test
	@DisplayName("createKakaoUser는 KAKAO USER를 생성할 수 있다.")
	void createUserWithKakaoOidc() {
		// given
		String oauthIdentity = "kakao-12345";

		// when
		user = User.createKakaoUser(oauthIdentity, NAME);

		// then
		assertNotNull(user);
		assertEquals(oauthIdentity, user.getOauthIdentity());
		assertEquals(NAME, user.getName());
		assertEquals(ATTENDEE, user.getRole());
		assertEquals(KAKAO, user.getProvider());
	}

	@Test
	@DisplayName("createGoogleUser는 GOOGLE USER를 생성할 수 있다.")
	void createUserWithGoogleOidc() {
		// given
		String oauthIdentity = "google-12345";

		// when
		user = User.createGoogleUser(oauthIdentity, NAME, EMAIL);

		// then
		assertNotNull(user);
		assertEquals(oauthIdentity, user.getOauthIdentity());
		assertEquals(NAME, user.getName());
		assertEquals(EMAIL, user.getEmail());
		assertEquals(ATTENDEE, user.getRole());
		assertEquals(GOOGLE, user.getProvider());
	}

	@Test
	@DisplayName("updateName은 유저 이름을 변경할 수 있다.")
	public void updateName_Success() {
		// given
		String newName = "김철수";

		// when
		user.updateName(newName);

		// then
		assertEquals(newName, user.getName());
	}

	@Test
	@DisplayName("updateRole은 유저 권한을 변경할 수 있다.")
	public void updateRole_Success() {
		// given
		BaseRole newRole = SPEAKER;

		// when
		user.updateRole(newRole);

		// then
		assertEquals(newRole, user.getRole());
	}

	@Test
	@DisplayName("updateInterest는 유저 관심사를 변경할 수 있다.")
	public void updateInterest_Success() {
		// given
		Interest newInterest = BACKEND;

		// when
		user.updateInterest(newInterest);

		// then
		assertEquals(newInterest, user.getInterest());
	}

	@Test
	@DisplayName("isPasswordMatched는 비밀번호가 일치하면 true를 반환한다.")
	public void isPasswordMatched_Success() {
		// given
		String rawPassword = "password1234!";
		String encodedPassword = PASSWORD_ENCODER.encode(rawPassword);
		User passwordUser = User.createAdmin(EMAIL, encodedPassword, NAME);

		// when
		boolean isMatched = passwordUser.isPasswordMatched(rawPassword, PASSWORD_ENCODER);

		// then
		assertTrue(isMatched);
	}
}
