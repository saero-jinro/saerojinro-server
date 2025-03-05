package notification.application;

import goorm.saerojinro.api.notification.application.NotificationFacade;
import goorm.saerojinro.domain.notification.application.NotificationCommandService;
import goorm.saerojinro.domain.notification.application.NotificationQueryService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.domain.notification.domain.NotificationRepository;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.UserRepository;
import mock.repository.FakeNotificationRepository;
import mock.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class NotificationFacadeTest {
	private NotificationFacade notificationFacade;

	@Mock
	private EmitterRepository emitterRepository;

	@BeforeEach
	public void init() {
		NotificationRepository repository = new FakeNotificationRepository();
		NotificationQueryService queryService = new NotificationQueryService(repository);
		NotificationCommandService commandService = new NotificationCommandService(repository);

		UserRepository userRepository = new FakeUserRepository();
		UserQueryService userQueryService = new UserQueryService(userRepository, new BCryptPasswordEncoder());

		notificationFacade = new NotificationFacade(commandService, queryService, emitterRepository, userQueryService);

	}


}
