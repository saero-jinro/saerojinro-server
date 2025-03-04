package goorm.saerojinro.domain.notification.application;

import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class NotificationQueryService {
	private final NotificationRepository notificationRepository;

	public List<Notification> findByUserId(Long userId) {
		return Stream.concat(
				notificationRepository.findByUserId(userId).stream(),
				notificationRepository.findByUserId(null).stream())
			.sorted(Comparator.comparing(Notification::getCreatedAt))
			.toList();
	}
}
