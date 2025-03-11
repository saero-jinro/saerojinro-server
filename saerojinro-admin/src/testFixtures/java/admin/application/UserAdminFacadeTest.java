package admin.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import goorm.saerojinro.admin.api.user.application.UserAdminFacade;
import goorm.saerojinro.admin.api.user.presentation.request.AdminCreateRequest;
import goorm.saerojinro.admin.api.user.presentation.response.AdminPersistResponse;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeUserRepository;

public class UserAdminFacadeTest {
	private UserAdminFacade userAdminFacade;
	private User user;

	@BeforeEach
	public void init() {
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		userAdminFacade = new UserAdminFacade(
			new UserCommandService(fakeUserRepository, bCryptPasswordEncoder)
		);

		user = fakeUserRepository.save(
			User.createKakaoUser(
				"identity",
				"name",
				"email@email.com",
				"profile.img")
		);
	}

	@Test
	@DisplayName("createAdmin은 새로운 관리자를 등록한다.")
	public void createAdmin_Success() {
		// given
		AdminCreateRequest request = new AdminCreateRequest(
			"password", "name", "email"
		);

		// when
		AdminPersistResponse response = userAdminFacade.createAdmin(request);

		// then
		assertEquals(2L, response.id());
	}
}
