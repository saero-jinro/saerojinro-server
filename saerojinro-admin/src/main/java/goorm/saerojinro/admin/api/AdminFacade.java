package goorm.saerojinro.admin.api;

import org.springframework.stereotype.Component;

import goorm.saerojinro.admin.presentation.request.AdminCreateRequest;
import goorm.saerojinro.admin.presentation.response.AdminPersistResponse;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminFacade {
	private final UserCommandService userCommandService;

	public AdminPersistResponse createAdmin(AdminCreateRequest request) {
		User admin = userCommandService.createAdmin(
			request.email(), request.password(), request.name()
		);

		return AdminPersistResponse.from(admin);
	}


}
