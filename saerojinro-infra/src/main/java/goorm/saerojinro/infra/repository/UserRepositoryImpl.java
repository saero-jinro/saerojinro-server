package goorm.saerojinro.infra.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import goorm.saerojinro.common.auth.domain.Provider;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.infra.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
	private final UserJpaRepository userJpaRepository;

	@Override
	public Optional<User> findByOauthIdentityAndProvider(String identifier,
		Provider provider) {
		return userJpaRepository.findByOauthIdentityAndProvider(identifier, provider);
	}

	@Override
	public User save(User user) {
		return userJpaRepository.save(user);
	}

	@Override
	public Optional<User> findByEamil(String email) {
		return userJpaRepository.findByEmail(email);
	}
}
