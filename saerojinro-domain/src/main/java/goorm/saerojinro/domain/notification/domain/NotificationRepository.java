package goorm.saerojinro.domain.notification.domain;

import java.util.Optional;

public interface NotificationRepository {
	Notification save(Notification notification);

	Optional<Notification> findByLectureId(Long lectureId);
}
