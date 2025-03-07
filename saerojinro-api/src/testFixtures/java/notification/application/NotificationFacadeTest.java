package notification.application;

import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.api.notification.presentation.response.ReceivedNotificationListResponse;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.domain.notification.domain.Notification;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.infra.notification.sse.NotificationSseSender;
import goorm.saerojinro.infra.repository.impl.EmitterRepositoryImpl;
import mock.repository.FakeNotificationRepository;
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

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class NotificationFacadeTest {
	private NotificationFacade notificationFacade;
	private NotificationRepository repository;

	private User user;

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
		NotificationSseSender sseSender = new NotificationSseSender(emitterRepository);

		notificationFacade = new NotificationFacade(sseSender, queryService, userQueryService);

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
	@DisplayName("myNotification은 현재 로그인한 유저가 받은 알림을 조회한다")
	void myNotification_Success() {
		// given
		repository.save(Notification.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.user(user)
			.build()
		);

		// when
		ReceivedNotificationListResponse response = notificationFacade.myNotification();

		// then
		assertNotNull(response);
		assertEquals(1, response.contents().size());
		assertEquals(TITLE, response.contents().get(0).title());
		assertEquals(CONTENTS, response.contents().get(0).contents());
	}

}