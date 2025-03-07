package goorm.saerojinro.admin.notification.application;

import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.infra.notification.exception.EmitterNotFoundException;
import goorm.saerojinro.infra.notification.request.NotificationSendRequest;
import goorm.saerojinro.infra.notification.response.NotificationSendResponse;
import goorm.saerojinro.infra.notification.sse.NotificationSseSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class NotificationAdminFacade {
	private final NotificationCommandService notificationCommandService;
	private final EmitterRepository emitterRepository;
	private final UserQueryService userQueryService;
	private final ReservationQueryService reservationQueryService;
	private final NotificationSseSender sseSender;

	@Transactional
	public void sendNotificationByLectureId(Long lectureId, NotificationSendRequest request) {
		List<Reservation> reservationList = reservationQueryService.getAllByLectureId(lectureId);
		for (Reservation reservation : reservationList) {
			Long receiverId = reservation.getUser().getId();
			sendNotificationByReceiverId(receiverId, request);
		}
	}

	@Transactional
	public void sendNotificationByReceiverId(Long receiverId, NotificationSendRequest request) {
		Notification notification = Notification.createNotification(
			userQueryService.getById(receiverId),
			request.title(),
			request.contents()
		);

		SseEmitter emitter = emitterRepository.findById(receiverId)
			.orElseThrow(EmitterNotFoundException::new);

		notificationCommandService.save(notification);
		NotificationSendResponse message = NotificationSendResponse.from(notification);

		sseSender.sendNotification(emitter, message);
	}

	@Transactional
	public void sendNotificationAll(NotificationSendRequest request) {
		Notification notification = Notification.createNotification(
			null,
			request.title(),
			request.contents()
		);
		notificationCommandService.save(notification);
		NotificationSendResponse message = NotificationSendResponse.from(notification);

		List<SseEmitter> all = emitterRepository.findAll();
		for (SseEmitter emitter : all) {
			sseSender.sendNotification(emitter, message);
		}
	}
}
