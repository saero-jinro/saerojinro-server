package notification.application;

import goorm.saerojinro.admin.notification.application.NotificationAdminEventHandler;
import goorm.saerojinro.admin.notification.application.NotificationAdminFacade;
import goorm.saerojinro.admin.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.common.event.CommonEvent;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.notification.application.EmitterQueryService;
import goorm.saerojinro.domain.notification.application.NotificationCommandService;
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

import java.util.List;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.event.EventType.BROADCAST_NOTICE;
import static goorm.saerojinro.common.event.EventType.LECTURE_IMMINENT;
import static goorm.saerojinro.common.event.EventType.LECTURE_NOTICE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationAdminEventHandlerTest {
	private NotificationAdminEventHandler notificationEventHandler;
	private NotificationRepository repository;
	private CommonEvent event;
	private final String TITLE = "title";
	private final String CONTENTS = "contents";

	@BeforeEach
	public void init() {
		repository = new FakeNotificationRepository();
		NotificationCommandService commandService = new NotificationCommandService(repository);
		EmitterRepository emitterRepository = new EmitterRepositoryImpl();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserRepository userRepository = new FakeUserRepository();
		UserQueryService userQueryService = new UserQueryService(userRepository, passwordEncoder);

		ReservationRepository reservationRepository = new FakeReservationRepository();
		ReservationQueryService reservationQueryService = new ReservationQueryService(reservationRepository);

		NotificationSseSender sseSender = new NotificationSseSender(emitterRepository);
		EmitterQueryService emitterQueryService = new EmitterQueryService(emitterRepository);
		NotificationAdminFacade notificationFacade = new NotificationAdminFacade(
			emitterQueryService, commandService, userQueryService, reservationQueryService, sseSender);

		notificationEventHandler = new NotificationAdminEventHandler(notificationFacade);

		User userEntity = userRepository.save(User.builder()
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
		emitterRepository.save(1L);
		Lecture lecture = Lecture.builder().id(1L).build();
		reservationRepository.save(Reservation.createReservation(userEntity, lecture));

		event = CommonEvent.builder()
			.eventType(BROADCAST_NOTICE)
			.lectureId(1L)
			.userId(1L)
			.title(TITLE)
			.contents(CONTENTS)
			.build();
	}

	@Test
	@DisplayName("handleEvent는 각 이벤트별 올바른 알림을 생성할 수 있다.")
	void handleEvent_Success() {
		// given
		// 강의 별 알림
		CommonEvent event2 = CommonEvent.builder().eventType(LECTURE_NOTICE)
			.lectureId(1L).title(TITLE).userId(1L).build();
		// 5분 전 알림
		CommonEvent event3 = CommonEvent.builder().eventType(LECTURE_IMMINENT)
			.lectureId(1L).title(TITLE).build();

		// when
		notificationEventHandler.handleEvent(event);
		notificationEventHandler.handleEvent(event2);
		notificationEventHandler.handleEvent(event3);

		// then
		List<Notification> all = repository.findByUserIdIsNull();
		assertEquals(1, all.size());
		assertNotNull(all);
		assertEquals("[" + event.eventType().getDescription() + "]" + TITLE, all.get(0).getTitle());

		List<Notification> my = repository.findByUserId(1L);
		assertEquals(2, my.size());
		assertEquals("[" + event2.eventType().getDescription() + "]" + TITLE, my.get(0).getTitle());
		assertEquals(TITLE, my.get(1).getTitle());
	}

	@Test
	@DisplayName("broadcastNotice는 전체 알림을 전송할 수 있다.")
	void broadcastNotice_Success() {
		// when
		notificationEventHandler.broadcastNotice(event);

		// then
		List<Notification> notifications = repository.findByUserIdIsNull();
		Notification result = notifications.get(0);
		assertNotNull(notifications);
		assertEquals("[" + event.eventType().getDescription() + "]" + TITLE, result.getTitle());
		assertEquals(CONTENTS, result.getContents());
	}

	@Test
	@DisplayName("lectureNotice는 강의별 알림을 전송할 수 있다.")
	void lectureNotice_Success() {
		// when
		notificationEventHandler.lectureNotice(event);

		// then
		List<Notification> notifications = repository.findByUserId(1L);
		Notification result = notifications.get(0);
		assertNotNull(notifications);
		assertEquals("[" + event.eventType().getDescription() + "]" + TITLE, result.getTitle());
		assertEquals(CONTENTS, result.getContents());
	}

	@Test
	@DisplayName("lectureImminent는 5분전 알림을 전송할 수 있다.")
	void lectureImminent_Success() {
		// when
		notificationEventHandler.lectureImminent(event);

		// then
		List<Notification> notifications = repository.findByUserId(1L);
		Notification result = notifications.get(0);
		assertNotNull(notifications);

		assertEquals(TITLE, result.getTitle());
		assertEquals(CONTENTS, result.getContents());

	}

	@Test
	@DisplayName("makeNotice는 알림 내용을 생성할 수 있다.")
	void makeNotice_Success() {
		// when
		NotificationSendRequest request = notificationEventHandler.makeNotice(event);

		// then
		assertNotNull(request);
		assertEquals("[" + event.eventType().getDescription() + "]" + TITLE, request.title());
		assertEquals(CONTENTS, request.contents());
	}
}
