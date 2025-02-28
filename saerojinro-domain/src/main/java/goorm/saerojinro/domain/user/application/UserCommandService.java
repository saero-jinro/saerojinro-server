package goorm.saerojinro.domain.user.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.domain.user.exception.InvalidPasswordException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCommandService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public void login(User user, String password) {
		if(!user.isPasswordMatched(password, bCryptPasswordEncoder)){
			throw new InvalidPasswordException();
		}
		// 추후 로직을 위해 command에 두었습니다
	}
}
