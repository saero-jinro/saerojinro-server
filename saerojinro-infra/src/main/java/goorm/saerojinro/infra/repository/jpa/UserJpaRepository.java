package goorm.saerojinro.infra.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import goorm.saerojinro.domain.user.domain.User;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
