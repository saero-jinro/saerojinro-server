package goorm.saerojinro.domain.user.domain;

import java.util.Optional;

import aj.org.objectweb.asm.commons.Remapper;
import goorm.saerojinro.common.auth.domain.Provider;

public interface UserRepository {
	Optional<User> findByOauthIdentityAndProvider(String identifier, Provider provider);

	User save(User user);
}
