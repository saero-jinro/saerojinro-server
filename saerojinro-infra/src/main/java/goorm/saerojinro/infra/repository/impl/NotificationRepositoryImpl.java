package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.infra.repository.jpa.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
	private final NotificationJpaRepository jpaRepository;
}
