package goorm.saerojinro.api.user.application;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.api.user.presentation.request.UserUpdateRequest;
import goorm.saerojinro.api.user.presentation.response.UserInfoResponse;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@PreAuthorize("hasRole('ATTENDEE')")
public class UserFacade {
	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;

	@Transactional
	public void update(UserUpdateRequest request) {
		Long id = userQueryService.me().getId();
		User user = userQueryService.getById(id);

		userCommandService.update(user, request.name(), request.email(), request.interest());
	}

	@Transactional
	public void delete() {
		User user = userQueryService.me();
		userCommandService.delete(user);
	}

	@Transactional(readOnly = true)
	public UserInfoResponse getCurrentUserInfo() {
		User user = userQueryService.me();
		return UserInfoResponse.from(user);
	}
}
