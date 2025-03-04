package goorm.saerojinro.domain.user.domain;

import java.util.Optional;

import aj.org.objectweb.asm.commons.Remapper;
import goorm.saerojinro.common.domain.Provider;

public interface UserRepository {
	User save(User user);

	Optional<User> findByEmail(String email);

	Optional<User> findByOauthIdentityAndProvider(String identifier, Provider provider);

	Optional<User> findById(Long userId);

}
