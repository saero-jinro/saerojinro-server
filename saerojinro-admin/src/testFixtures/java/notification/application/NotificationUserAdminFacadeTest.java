package notification.application;

import goorm.saerojinro.admin.api.notification.application.NotificationAdminFacade;
import goorm.saerojinro.admin.api.notification.presentation.request.NotificationSendRequest;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationUserAdminFacadeTest {
	private NotificationAdminFacade notificationFacade;
	private NotificationRepository repository;
	private ReservationRepository reservationRepository;

	private User user;

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

		reservationRepository = new FakeReservationRepository();
		ReservationQueryService reservationQueryService = new ReservationQueryService(reservationRepository);

		NotificationSseSender sseSender = new NotificationSseSender(emitterRepository);
		EmitterQueryService emitterQueryService = new EmitterQueryService(emitterRepository);

		notificationFacade = new NotificationAdminFacade(
			emitterQueryService, commandService, userQueryService, reservationQueryService, sseSender);

		user = userRepository.save(User.builder()
			.email("email@email.com")
			.password(passwordEncoder.encode("password1234!"))
			.name("박민준")
			.role(ADMIN)
			.build()
		);

		emitterRepository.save(1L);

		UserDetails user = userQueryService.getByEmail("email@email.com");
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
		);
	}

	@Test
	@DisplayName("sendNotificationByLectureId은 Lecture를 예약한 참가자에게 알림을 전송한다")
	void sendNotificationByLectureId_Success() {
		// given
		Lecture lecture = Lecture.builder().id(1L).build();
		reservationRepository.save(Reservation.create(user, lecture));

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
}