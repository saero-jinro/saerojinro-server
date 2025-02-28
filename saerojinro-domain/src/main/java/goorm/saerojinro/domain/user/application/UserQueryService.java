package goorm.saerojinro.domain.user.application;

import org.springframework.stereotype.Service;

import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserQueryService {
	private final UserRepository userRepository;

	public User getByEmail(String email) {
		return userRepository.findByEamil(email)
			.orElseThrow(UserNotFoundException::new);
	}
}
