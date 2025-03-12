package goorm.saerojinro.admin.api.user.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.admin.api.user.presentation.request.AdminCreateRequest;
import goorm.saerojinro.admin.api.user.presentation.response.AdminPersistResponse;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserAdminFacade {
	private final UserCommandService userCommandService;

	@Transactional
	public AdminPersistResponse createAdmin(AdminCreateRequest request) {
		User admin = userCommandService.createAdmin(
			request.email(), request.password(), request.name()
		);

		return AdminPersistResponse.from(admin);
	}
}
