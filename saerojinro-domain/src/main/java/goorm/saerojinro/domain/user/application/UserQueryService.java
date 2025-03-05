package goorm.saerojinro.domain.user.application;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.domain.user.exception.InvalidPasswordException;
import goorm.saerojinro.domain.user.exception.UserNotAuthenticatedException;
import goorm.saerojinro.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueryService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public User getByEmail(String email) {
		return userRepository.findByEmail(email)
			.orElseThrow(UserNotFoundException::new);
	}

	public User login(String email, String password) {
		User user = getByEmail(email);
		if (!user.isPasswordMatched(password, bCryptPasswordEncoder)) {
			throw new InvalidPasswordException();
		}
		return user;
	}

	public User getById(Long Id){
		return userRepository.findById(Id)
				.orElseThrow(UserNotFoundException::new);
	}

	public User me() {
		try {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String email = ((UserDetails)principal).getUsername();
			return getByEmail(email);
		} catch (Exception e) {
			throw new UserNotAuthenticatedException();
		}
	}
}
