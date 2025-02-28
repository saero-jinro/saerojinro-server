package goorm.saerojinro.domain.user.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
