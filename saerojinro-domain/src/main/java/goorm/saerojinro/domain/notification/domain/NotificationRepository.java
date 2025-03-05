package goorm.saerojinro.domain.notification.domain;

import java.util.List;

public interface NotificationRepository {
	Notification save(Notification notification);

	List<Notification> findByUserId(Long lectureId);

	List<Notification> findByUserIdIsNull();
}
