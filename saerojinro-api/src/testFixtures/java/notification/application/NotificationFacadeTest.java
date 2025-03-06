package notification.application;

import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.infra.notification.sse.NotificationSseSender;
import goorm.saerojinro.infra.repository.impl.EmitterRepositoryImpl;
import mock.repository.FakeNotificationRepository;
import mock.repository.FakeReservationRepository;
import mock.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationFacadeTest {
	private NotificationFacade notificationFacade;
	private NotificationRepository repository;
	private ReservationRepository reservationRepository;

	private User user;

	private final String TITLE = "title";
	private final String CONTENTS = "contents";

	@BeforeEach
	public void init() {
		repository = new FakeNotificationRepository();
		NotificationQueryService queryService = new NotificationQueryService(repository);
		NotificationCommandService commandService = new NotificationCommandService(repository);
		EmitterRepository emitterRepository = new EmitterRepositoryImpl();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserRepository userRepository = new FakeUserRepository();
		UserQueryService userQueryService = new UserQueryService(userRepository, passwordEncoder);

		reservationRepository = new FakeReservationRepository();
		ReservationQueryService reservationQueryService = new ReservationQueryService(reservationRepository);

		NotificationSseSender sseSender = new NotificationSseSender();

		notificationFacade = new NotificationFacade(
			commandService, queryService, emitterRepository, userQueryService, reservationQueryService, sseSender);

		user = userRepository.save(User.builder()
			.email("email@email.com")
			.password(passwordEncoder.encode("password1234!"))
			.name("박민준")
			.role(ADMIN)
			.build()
		);

		UserDetails user = userQueryService.getByEmail("email@email.com");
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
		);
	}

	@Test
	@DisplayName("subscribe는 SseEmitter를 생성한다")
	void subscribe_Success() {
		// when
		SseEmitter emitter = notificationFacade.subscribe();

		// then
		assertNotNull(emitter);
	}

	@Test
	@DisplayName("sendNotificationByLectureId은 Lecture를 예약한 참가자에게 알림을 전송한다")
	void sendNotificationByLectureId_Success() {
		// given
		notificationFacade.subscribe();
		Lecture lecture = Lecture.builder().id(1L).build();
		reservationRepository.save(Reservation.createReservation(user, lecture));

		NotificationSendRequest request = NotificationSendRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.build();

		// when
		notificationFacade.sendNotificationByLectureId(1L, request);

		// then
		List<Notification> notifications = repository.findByUserId(user.getId());
		Notification result = notifications.get(0);
		assertNotNull(notifications);
		assertEquals(TITLE, result.getTitle());
		assertEquals(CONTENTS, result.getContents());
	}

	@Test
	@DisplayName("sendNotificationByReceiverId는 특정 참가자에게 알림을 전송한다")
	void sendNotificationByReceiverId_Success() {
		// given
		notificationFacade.subscribe();
		Long receiverId = 1L;
		NotificationSendRequest request = NotificationSendRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.build();

		// when
		notificationFacade.sendNotificationByReceiverId(receiverId, request);

		// then
		List<Notification> notifications = repository.findByUserId(receiverId);
		Notification result = notifications.get(0);
		assertNotNull(notifications);
		assertEquals(TITLE, result.getTitle());
		assertEquals(CONTENTS, result.getContents());
	}


	@Test
	@DisplayName("sendNotificationAll는 모든 참가자에게 알림을 전송한다")
	void sendNotificationAll_Success() {
		// given
		notificationFacade.subscribe();
		NotificationSendRequest request = NotificationSendRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.build();

		// when
		notificationFacade.sendNotificationAll(request);

		// then
		List<Notification> notifications = repository.findByUserIdIsNull();
		Notification result = notifications.get(0);
		assertNotNull(notifications);
		assertEquals(TITLE, result.getTitle());
		assertEquals(CONTENTS, result.getContents());
	}

	@Test
	@DisplayName("myNotification은 현재 로그인한 유저가 받은 알림을 조회한다")
	void myNotification_Success() {
		// given
		notificationFacade.subscribe();
		Long receiverId = 1L;
		NotificationSendRequest notification = NotificationSendRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.build();
		notificationFacade.sendNotificationByReceiverId(receiverId, notification);

		// when
		ReceivedNotificationListResponse response = notificationFacade.myNotification();

		// then
		assertNotNull(response);
		assertEquals(1, response.contents().size());
		assertEquals(TITLE, response.contents().get(0).title());
		assertEquals(CONTENTS, response.contents().get(0).contents());
	}

}
