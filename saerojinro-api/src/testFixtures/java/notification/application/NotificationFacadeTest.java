package notification.application;

import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.infra.repository.impl.EmitterRepositoryImpl;
import mock.repository.FakeNotificationRepository;
import mock.repository.FakeUserRepository;
import org.junit.jupiter.api.Assertions;
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
	private UserRepository userRepository;

	private final String TITLE = "title";
	private final String CONTENTS = "contents";

	@BeforeEach
	public void init() {
		repository = new FakeNotificationRepository();
		NotificationQueryService queryService = new NotificationQueryService(repository);
		NotificationCommandService commandService = new NotificationCommandService(repository);
		EmitterRepository emitterRepository = new EmitterRepositoryImpl();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		userRepository = new FakeUserRepository();
		UserQueryService userQueryService = new UserQueryService(userRepository, passwordEncoder);

		notificationFacade = new NotificationFacade(commandService, queryService, emitterRepository, userQueryService);

		userRepository.save(User.builder()
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
	@DisplayName("subscribe")
	void subscribe_Success() {
		// when
		SseEmitter emitter = notificationFacade.subscribe();

		// then
		assertNotNull(emitter);
	}

	// TODO ReservationService 이후
	//	@Test
	@DisplayName("sendNotificationByLectureIdWithRequest")
	void sendNotificationByLectureIdWithRequest_Success() {
		// given
		NotificationSendRequest request = NotificationSendRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.build();

		// when
//		notificationFacade.sendNotificationByLectureIdWithRequest();

		// then

	}

	@Test
	@DisplayName("sendNotificationWithRequest")
	void sendNotificationWithRequest_Success() {
		// given
		notificationFacade.subscribe();
		Long receiverId = 1L;
		NotificationSendRequest request = NotificationSendRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.build();

		// when
		notificationFacade.sendNotificationWithRequest(receiverId, request);

		// then
		List<Notification> notifications = repository.findByUserId(receiverId);
		Notification result = notifications.get(0);
		assertNotNull(notifications);
		Assertions.assertEquals(TITLE, result.getTitle());
		Assertions.assertEquals(CONTENTS, result.getContents());
	}

	@Test
	@DisplayName("sendNotification")
	void sendNotification_Success() {
		// given
		notificationFacade.subscribe();
		Long receiverId = 1L;
		Notification notification = Notification.createNotification(
			userRepository.findById(1L).get(), TITLE, CONTENTS
		);

		// when
		notificationFacade.sendNotification(receiverId, notification);

		// then
		List<Notification> notifications = repository.findByUserId(receiverId);
		Notification result = notifications.get(0);
		assertNotNull(notifications);
		Assertions.assertEquals(TITLE, result.getTitle());
		Assertions.assertEquals(CONTENTS, result.getContents());
	}


	@Test
	@DisplayName("sendNotificationAll")
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
		Assertions.assertEquals(TITLE, result.getTitle());
		Assertions.assertEquals(CONTENTS, result.getContents());
	}

	@Test
	@DisplayName("myNotification")
	void myNotification_Success() {
		// given
		notificationFacade.subscribe();
		Long receiverId = 1L;
		Notification notification = Notification.createNotification(
			userRepository.findById(1L).get(), TITLE, CONTENTS
		);
		notificationFacade.sendNotification(receiverId, notification);

		// when
		ReceivedNotificationListResponse response = notificationFacade.myNotification();

		// then
		assertNotNull(response);
		assertEquals(1, response.contents().size());
		assertEquals(TITLE, response.contents().get(0).title());
		assertEquals(CONTENTS, response.contents().get(0).contents());
	}

}
