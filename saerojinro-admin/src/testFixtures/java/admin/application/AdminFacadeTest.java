package admin.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import goorm.saerojinro.admin.api.application.AdminFacade;
import goorm.saerojinro.admin.api.presentation.request.AdminCreateRequest;
import goorm.saerojinro.admin.api.presentation.response.AdminPersistResponse;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.application.UserQueryService;
import mock.repository.FakeUserRepository;

public class AdminFacadeTest {
	private AdminFacade adminFacade;

	@BeforeEach
	public void init() {
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		adminFacade = new AdminFacade(
			new UserCommandService(fakeUserRepository, bCryptPasswordEncoder),
			new UserQueryService(fakeUserRepository, bCryptPasswordEncoder)
		);
	}

	@Test
	@DisplayName("createAdmin은 새로운 관리자를 등록한다.")
	public void createAdmin() {
		// given
		AdminCreateRequest request = new AdminCreateRequest(
			"password", "name", "email"
		);

		// when
		AdminPersistResponse response = adminFacade.createAdmin(request);

		// then
		assertEquals(1L, response.id());
	}
}
