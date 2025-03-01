package goorm.saerojinro.domain.notification.application;

import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationCommandService {
	private final NotificationRepository notificationRepository;

	public Long save(Notification notification) {
		return notificationRepository.save(notification).getId();
	}
}
