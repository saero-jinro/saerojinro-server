package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {
}
