package notification.application;

import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.api.notification.presentation.request.NotificationSendRequest;
import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
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

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;

public class NotificationFacadeTest {
	private NotificationFacade notificationFacade;

	@BeforeEach
	public void init() {
		NotificationRepository repository = new FakeNotificationRepository();
		NotificationQueryService queryService = new NotificationQueryService(repository);
		NotificationCommandService commandService = new NotificationCommandService(repository);
		EmitterRepository emitterRepository = new EmitterRepositoryImpl();

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserRepository userRepository = new FakeUserRepository();
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
		Assertions.assertNotNull(emitter);
	}

	// TODO LectureService 이후
	//	@Test
	@DisplayName("sendNotificationByLectureIdWithRequest")
	void sendNotificationByLectureIdWithRequest_Success() {
		// given
		NotificationSendRequest request = NotificationSendRequest.builder()
			.title("title")
			.contents("contents")
			.build();

		// when
//		notificationFacade.sendNotificationByLectureIdWithRequest();

		// then

	}

	@Test
	@DisplayName("sendNotificationWithRequest")
	void sendNotificationWithRequest_Success() {
		// when
		notificationFacade.subscribe();

		// then

	}


	@Test
	@DisplayName("sendNotification")
	void sendNotification_Success() {
		// when
		notificationFacade.subscribe();

		// then

	}


	@Test
	@DisplayName("sendNotificationAll")
	void sendNotificationAll_Success() {
		// when
		notificationFacade.subscribe();

		// then

	}

	@Test
	@DisplayName("ReceivedNotificationListResponse")
	void ReceivedNotificationListResponse_Success() {
		// when
		notificationFacade.subscribe();

		// then

	}

}
