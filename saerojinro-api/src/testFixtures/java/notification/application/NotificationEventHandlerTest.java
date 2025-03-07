package notification.application;

import goorm.saerojinro.api.notification.application.NotificationEventHandler;
import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.common.event.CommonEvent;
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
import goorm.saerojinro.infra.notification.request.NotificationSendRequest;
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
import static goorm.saerojinro.common.event.EventType.LECTURE_APPROVED;
import static goorm.saerojinro.common.event.EventType.LECTURE_CREATE;
import static goorm.saerojinro.common.event.EventType.LECTURE_DELETE;
import static goorm.saerojinro.common.event.EventType.LECTURE_NOTICE;
import static goorm.saerojinro.common.event.EventType.SPEAKER_APPROVED;
import static goorm.saerojinro.common.event.EventType.SPEAKER_CREATE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationEventHandlerTest {
	private NotificationEventHandler notificationEventHandler;
	private NotificationRepository repository;
	private CommonEvent event;
	private final String TITLE = "title";
	private final String CONTENTS = "contents";

	@BeforeEach
	public void init() {
		repository = new FakeNotificationRepository();
		NotificationQueryService queryService = new NotificationQueryService(repository);
		EmitterRepository emitterRepository = new EmitterRepositoryImpl();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserRepository userRepository = new FakeUserRepository();
		UserQueryService userQueryService = new UserQueryService(userRepository, passwordEncoder);

		ReservationRepository reservationRepository = new FakeReservationRepository();

		NotificationFacade notificationFacade = new NotificationFacade(
			emitterRepository, queryService, userQueryService);

		notificationEventHandler = new NotificationEventHandler(notificationFacade);

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
	@DisplayName("API의 handleEvent는 알림을 생성할 수 없다.")
	void handleEvent_Success() {
		// given
		CommonEvent event2 = CommonEvent.builder().eventType(LECTURE_NOTICE)
			.lectureId(1L).title(TITLE).userId(1L).build();
		CommonEvent event3 = CommonEvent.builder().eventType(SPEAKER_CREATE).userId(1L).build();
		CommonEvent event4 = CommonEvent.builder().eventType(SPEAKER_APPROVED).userId(1L).build();
		CommonEvent event5 = CommonEvent.builder().eventType(LECTURE_CREATE).userId(1L).build();
		CommonEvent event6 = CommonEvent.builder().eventType(LECTURE_APPROVED).userId(1L).build();
		CommonEvent event7 = CommonEvent.builder().eventType(LECTURE_DELETE).userId(1L).build();

		// when
		notificationEventHandler.handleEvent(event);
		notificationEventHandler.handleEvent(event2);
		notificationEventHandler.handleEvent(event3);
		notificationEventHandler.handleEvent(event4);
		notificationEventHandler.handleEvent(event5);
		notificationEventHandler.handleEvent(event6);
		notificationEventHandler.handleEvent(event7);

		// then
		List<Notification> all = repository.findByUserIdIsNull();
		assertEquals(0, all.size());

		List<Notification> my = repository.findByUserId(1L);
		assertEquals(0, my.size());
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
