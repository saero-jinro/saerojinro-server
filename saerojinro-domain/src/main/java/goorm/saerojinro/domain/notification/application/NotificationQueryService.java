package goorm.saerojinro.domain.notification.application;

import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.domain.notification.exception.NotificationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationQueryService {
	private final NotificationRepository notificationRepository;

	public Notification findByLectureId(Long lectureId) {
		return notificationRepository.findByLectureId(lectureId)
			.orElseThrow(NotificationNotFoundException::new);
	}
}
