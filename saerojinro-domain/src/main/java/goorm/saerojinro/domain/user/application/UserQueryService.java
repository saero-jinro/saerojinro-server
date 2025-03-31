package goorm.saerojinro.domain.user.application;


import static goorm.saerojinro.common.domain.Provider.KAKAO;

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
			Long id = Long.valueOf(getAuthenticatedUsername());
			if (isAnonymous(id)) return null;
			return getById(id);
		} catch (Exception e) {
			return null;
		}
	}

	public User getByOauthId(String oauthIdentity) {
		return userRepository.findByOauthIdentityAndProvider(oauthIdentity, KAKAO)
			.orElse(null);
	}

	public String getAuthenticatedUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication.getPrincipal() == null || !(authentication.getPrincipal() instanceof UserDetails)) {
			return "anonymous";
		}

		return ((UserDetails) authentication.getPrincipal()).getUsername();
	}

	public boolean isAnonymous(Long id) {
		return "anonymous".equals(id.toString());
	}
}
