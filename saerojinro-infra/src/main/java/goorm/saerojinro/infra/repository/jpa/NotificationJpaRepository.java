package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {
	List<Notification> findAllByUserIdAndDeletedAtIsNullOrderByCreatedAt(Long userId);
}
