package user.application;

import static goorm.saerojinro.common.domain.Category.BACKEND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeUserRepository;

public class UserCommandServiceTest {
	private UserCommandService userCommandService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private FakeUserRepository fakeUserRepository;
	private User user;

	@BeforeEach
	public void init() {
		fakeUserRepository = new FakeUserRepository();
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
		user = userCommandService.createAdmin(email, password, name);

		// then
		assertEquals(email, user.getEmail());
		assertTrue(bCryptPasswordEncoder.matches(password, user.getPassword()));
		assertEquals(name, user.getName());
	}

	@Test
	@DisplayName("kakaoSocialLogin은 신규 유저 생성이 가능하다.")
	void kakaoSocialLogin_CreateNewUser() {
		// given
		String oauthIdentity = "kakao_12345";
		String name = "박민준";
		String email = "minjun@kakao.com";
		String profileImage = "http://kakao.com/profile.png";

		// when
		user = userCommandService.kakaoSocialLogin(oauthIdentity, name, email, profileImage);

		// then
		assertEquals(oauthIdentity, user.getOauthIdentity());
		assertEquals(name, user.getName());
		assertEquals(email, user.getEmail());
		assertEquals(profileImage, user.getProfileImage());
	}

	@Test
	@DisplayName("kakaoSocialLogin은 기존 유저 정보 업데이트가 가능하다.")
	void kakaoSocialLogin_UpdateExistingUser() {
		// given
		String oauthIdentity = "kakao_12345";
		String originalName = "박민준";
		String originalEmail = "minjun@kakao.com";
		String originalProfileImage = "http://kakao.com/old_profile.png";

		userCommandService.kakaoSocialLogin(oauthIdentity, originalName, originalEmail, originalProfileImage);

		String updatedName = "민준박";
		String updatedEmail = "minjun.new@kakao.com";
		String updatedProfileImage = "http://kakao.com/new_profile.png";

		// when
		User updatedUser = userCommandService.kakaoSocialLogin(oauthIdentity, updatedName, updatedEmail, updatedProfileImage);

		// then
		assertEquals(oauthIdentity, updatedUser.getOauthIdentity());
		assertEquals(updatedName, updatedUser.getName());
		assertEquals(updatedEmail, updatedUser.getEmail());
		assertEquals(updatedProfileImage, updatedUser.getProfileImage());
	}

	@Test
	@DisplayName("update는 User의 정보를 수정할 수 있다")
	public void update_Success() {
		// given
		User user = User.builder().build();
		String newName = "test";
		String newEmail = "email@email.com";
		Category newInterest = BACKEND;

		// when
		userCommandService.update(user, newName, newEmail, newInterest);

		// then
		assertEquals(newName, user.getName());
		assertEquals(newEmail, user.getEmail());
		assertEquals(newInterest, user.getInterest());
	}

	@Test
	@DisplayName("delete는 해당 User를 삭제한다")
	public void delete_Success() {
		// given
		String email = "admin@gmail.com";
		String password = "password";
		String name = "admin";

		User user = userCommandService.createAdmin(email, password, name);

		// when
		userCommandService.delete(user);

		// then
		assertThat(fakeUserRepository.findByEmail(email)).isEmpty();
	}
}
