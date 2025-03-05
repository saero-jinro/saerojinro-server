package goorm.saerojinro.infra.repository.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import goorm.saerojinro.common.domain.Provider;
import goorm.saerojinro.domain.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmailAndDeletedAtIsNull(String email);

	Optional<User> findByOauthIdentityAndProvider(String identifier, Provider provider);

	Optional<User> findByIdAndDeletedAtIsNull(Long id);
}
