package goorm.saerojinro.domain.user.application;

import static goorm.saerojinro.common.domain.Provider.KAKAO;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.common.domain.Provider;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCommandService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public User createAdmin(String email, String password, String name) {
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		User user = User.createAdmin(email, encodedPassword, name);
		return userRepository.save(user);
	}

	public User kakaoSocialLogin(String oauthIdentity, String name, String email, String profileImage) {
		return userRepository.findByOauthIdentityAndProvider(oauthIdentity, KAKAO)
			.map(user -> {
				update(user, name, email, null);
				user.updateProfileImage(profileImage);
				return userRepository.save(user);
			})
			.orElseGet(() -> {
				User newUser = User.createKakaoUser(oauthIdentity, name, email, profileImage);
				return userRepository.save(newUser);
			});
	}

	public void update(User user, String name, String email, Category category) {
		user.updateName(name);
		user.updateEmail(email);
		user.updateInterest(category);
	}
}
