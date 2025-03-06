package mock.repository;

import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeNotificationRepository implements NotificationRepository {
	private final List<Notification> data = Collections.synchronizedList(new ArrayList<>());
	private final AtomicLong sequence = new AtomicLong(0);

	@Override
	public Notification save(Notification notification) {
		Notification build = Notification.builder()
			.id(sequence.incrementAndGet())
			.user(notification.getUser())
			.title(notification.getTitle())
			.contents(notification.getContents())
			.build();

		data.add(build);
		return build;
	}

	@Override
	public List<Notification> findByUserId(Long userId) {
		return data.stream()
			.filter(n -> n.getUser() != null)
			.filter(n -> n.getUser().getId().equals(userId))
			.toList();
	}

	@Override
	public List<Notification> findByUserIdIsNull() {
		return data.stream()
			.filter(n -> n.getUser() == null)
			.toList();
	}
}
