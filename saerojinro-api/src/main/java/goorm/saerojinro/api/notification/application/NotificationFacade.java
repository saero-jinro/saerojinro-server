package goorm.saerojinro.api.notification.application;

import goorm.saerojinro.api.notification.presentation.exception.EmitterNotFoundException;
import goorm.saerojinro.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.api.notification.presentation.response.NotificationSendResponse;
import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
	private final NotificationCommandService notificationCommandService;
	private final NotificationQueryService notificationQueryService;
	private final EmitterRepository emitterRepository;
	private final UserQueryService userQueryService;
	private final ReservationQueryService reservationQueryService;

	public SseEmitter subscribe() {
		User user = userQueryService.me();
		return emitterRepository.save(user.getId());
	}

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

		sendNotification(receiverId, notification);
	}

	@Transactional
	public void sendNotification(Long receiverId, Notification notification) {
		SseEmitter emitter = emitterRepository.findById(receiverId)
			.orElseThrow(EmitterNotFoundException::new);

		notificationCommandService.save(notification);
		NotificationSendResponse message = NotificationSendResponse.from(notification);

		try {
			emitter.send(SseEmitter.event().data(message));
		} catch (IOException e) {
			emitter.completeWithError(e);
		}
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
			try {
				emitter.send(SseEmitter.event().data(message));
			} catch (IOException e) {
				emitter.completeWithError(e);
			}
		}
	}

	@Transactional(readOnly = true)
	public ReceivedNotificationListResponse myNotification() {
		User user = userQueryService.me();
		List<Notification> notificationList = notificationQueryService.findByUserId(user.getId());
		return ReceivedNotificationListResponse.from(notificationList);
	}
}
