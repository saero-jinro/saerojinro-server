package goorm.saerojinro.api.user.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goorm.saerojinro.api.user.application.UserFacade;
import goorm.saerojinro.api.user.presentation.request.UserUpdateRequest;
import goorm.saerojinro.api.user.presentation.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class UserControllerImpl implements UserController{
	private final UserFacade userFacade;

	@Override
	@PatchMapping
	public ResponseEntity<Void> update(UserUpdateRequest request) {
		userFacade.update(request);
		return ResponseEntity.noContent().build();
	}

	@Override
	@DeleteMapping
	public ResponseEntity<Void> delete() {
		userFacade.delete();
		return ResponseEntity.noContent().build();
	}

	@Override
	@GetMapping("/me")
	public ResponseEntity<UserInfoResponse> getUserInfo() {
		UserInfoResponse response = userFacade.getCurrentUserInfo();
		return ResponseEntity.ok(response);
	}
}
