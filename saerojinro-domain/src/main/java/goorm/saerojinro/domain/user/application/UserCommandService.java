package goorm.saerojinro.domain.user.application;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import goorm.saerojinro.common.domain.Category;
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

	public void updateSocialInfo(User user, String name, String email, String profileImage) {
		user.updateName(name);
		user.updateEmail(email);
		user.updateProfileImage(profileImage);
	}

	public User createKakaoUser(String oauthIdentity, String name, String email, String profileImage) {
		User user = User.createKakaoUser(oauthIdentity, name, email, profileImage);
		return userRepository.save(user);
	}

	@CacheEvict(value = "users", key = "#user.getEmail()")
	public void update(User user, String name, String email, Category category) {
		user.updateName(name);
		user.updateEmail(email);
		user.updateInterest(category);
	}

	public void delete(User user) {
		user.delete();
	}
}
