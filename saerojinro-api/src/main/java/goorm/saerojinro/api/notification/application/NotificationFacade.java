package goorm.saerojinro.api.notification.application;

import goorm.saerojinro.api.notification.presentation.exception.EmitterNotFoundException;
import goorm.saerojinro.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.api.notification.presentation.response.NotificationSendResponse;
import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.infra.repository.impl.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationFacade {
	private final NotificationCommandService notificationCommandService;
	private final NotificationQueryService notificationQueryService;
	private final EmitterRepository emitterRepository;
	// TODO commandService로 교체
//	private final LectureCommandService lectureCommandService;
	private final LectureRepository lectureRepository;
	private final UserQueryService userQueryService;
	private final UserRepositoryImpl userRepositoryImpl;

	public SseEmitter subscribe() {
		// TODO 현재 로그인한 유저 가져오기
		User user = User.builder().build();
		SseEmitter emitter = new SseEmitter();
		return emitterRepository.save(user.getId(), emitter);
	}

	public void sendNotificationWithRequest(NotificationSendRequest request) {
//		Notification notification = Notification.createNotification(
//			// TODO userService
//			userRepositoryImpl.findById(request.receiverId())
//			request.title(),
//			request.contents()
//		);
//
//		sendNotification(request.receiverId(), notification);
	}

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

	public void sendNotificationAll(Notification notification) {
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

	public ReceivedNotificationListResponse myNotification() {
		// TODO 현재 로그인한 유저 가져오기
		User user = User.builder().build();

		// user -> 예약한 강의 찾기 -> lectureId
		Long lectureId = null;

		List<Notification> notificationList = notificationQueryService.findByUserId(lectureId);
		return ReceivedNotificationListResponse.from(notificationList);
	}
}
