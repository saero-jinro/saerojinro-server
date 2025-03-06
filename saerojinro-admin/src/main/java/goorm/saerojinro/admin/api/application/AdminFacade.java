package goorm.saerojinro.admin.api.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.admin.api.presentation.request.AdminCreateRequest;
import goorm.saerojinro.admin.api.presentation.request.RoleUpdateRequest;
import goorm.saerojinro.admin.api.presentation.response.AdminPersistResponse;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminFacade {
	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;

	@Transactional
	public AdminPersistResponse createAdmin(AdminCreateRequest request) {
		User admin = userCommandService.createAdmin(
			request.email(), request.password(), request.name()
		);

		return AdminPersistResponse.from(admin);
	}

	@Transactional
	public void updateRole(Long userId, RoleUpdateRequest request) {
		User user = userQueryService.getById(userId);
		userCommandService.updateRole(user, request.permission());
	}

}
