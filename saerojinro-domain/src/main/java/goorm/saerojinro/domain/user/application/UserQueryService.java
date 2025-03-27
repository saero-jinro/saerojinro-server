package goorm.saerojinro.domain.user.application;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.domain.user.exception.InvalidPasswordException;
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

	@Cacheable(value = "users", key = "#root.target.getAuthenticatedUsername()", unless = "#result == null or #root.target.isAnonymous(#root.target.getAuthenticatedUsername())")
	public User me() {
		try {
			String email = getAuthenticatedUsername();
			if (isAnonymous(email)) return null;
			return getByEmail(email);
		} catch (Exception e) {
			return null;
		}
	}

	public String getAuthenticatedUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof UserDetails)) {
			return "anonymous";
		}

		return ((UserDetails) authentication.getPrincipal()).getUsername();
	}

	public boolean isAnonymous(String username) {
		return "anonymous".equals(username);
	}
}
