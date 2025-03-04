package goorm.saerojinro.domain.notification.application;

import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationQueryService {
	private final NotificationRepository notificationRepository;

	public List<Notification> findByLectureId(Long lectureId) {
		return notificationRepository.findByLectureId(lectureId);
	}
}
