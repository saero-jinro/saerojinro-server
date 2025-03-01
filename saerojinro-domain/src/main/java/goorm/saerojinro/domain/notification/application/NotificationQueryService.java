package goorm.saerojinro.domain.notification.application;

import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationQueryService {
	private final NotificationRepository notificationRepository;
}
