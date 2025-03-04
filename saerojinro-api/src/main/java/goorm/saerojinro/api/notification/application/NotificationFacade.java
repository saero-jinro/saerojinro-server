package goorm.saerojinro.api.notification.application;

import goorm.saerojinro.api.notification.presentation.exception.EmitterNotFoundException;
import goorm.saerojinro.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.api.notification.presentation.response.NotificationSendResponse;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.user.domain.User;
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

	public SseEmitter subscribe() {
		// TODO 현재 로그인한 유저 가져오기
		User user = User.builder().build();
		SseEmitter emitter = new SseEmitter();
		return emitterRepository.save(user.getId(), emitter);
	}

	public void sendNotificationByLectureId(Long lectureId, NotificationSendRequest request) {
		// TODO
		// lecutureId -> lecture -> 예약한 참가자 찾기 -> 참가자들에게 알림 전송
//		receiverIds.forEach(receiverId -> sendNotificationWithRequest(receiverId, request));
	}

	public void sendNotificationWithRequest(Long receiverId, NotificationSendRequest request) {
		Notification notification = Notification.createNotification(
			// TODO commandService
			lectureRepository.findById(request.lectureId()).get(),
			request.title(),
			request.contents()
		);

		sendNotification(receiverId, notification);
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

	public
}
