package goorm.saerojinro.api.user.application;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import goorm.saerojinro.api.user.presentation.request.UserUpdateRequest;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserFacade {
	private final UserCommandService userCommandService;
	private final UserQueryService userQueryService;

	@Transactional
	public void update(UserUpdateRequest request) {
		User user = userQueryService.me();
		userCommandService.update(user, request.name(), request.email(), request.interest());
	}

	@Transactional
	public void delete() {
		User user = userQueryService.me();
		userCommandService.delete(user);
	}
}
