package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.notification.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationJpaRepository extends JpaRepository<Notification, Long> {
	Optional<Notification> findByLectureIdAndDeletedAtIsNull(Long lectureId);
}
