package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.infra.repository.jpa.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
	private final NotificationJpaRepository jpaRepository;

	@Override
	public Notification save(Notification notification) {
		return jpaRepository.save(notification);
	}

	@Override
	public List<Notification> findByUserId(Long userId) {
		return jpaRepository.findAllByUserIdAndDeletedAtIsNullOrderByCreatedAt(userId);
	}

	@Override
	public List<Notification> findByUserIdIsNull() {
		return jpaRepository.findAllByUserIdIsNullAndDeletedAtIsNullOrderByCreatedAt();
	}
}
