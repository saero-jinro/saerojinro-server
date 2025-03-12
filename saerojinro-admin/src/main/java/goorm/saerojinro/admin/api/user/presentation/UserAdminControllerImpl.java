package goorm.saerojinro.admin.api.user.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goorm.saerojinro.admin.api.user.application.UserAdminFacade;
import goorm.saerojinro.admin.api.user.presentation.request.AdminCreateRequest;
import goorm.saerojinro.admin.api.user.presentation.response.AdminPersistResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserAdminControllerImpl implements UserAdminController {
	private final UserAdminFacade userAdminFacade;

	@Override
	@PostMapping
	public ResponseEntity<AdminPersistResponse> createAdmin(AdminCreateRequest request) {
		AdminPersistResponse response = userAdminFacade.createAdmin(request);
		return ResponseEntity.status(CREATED).body(response);
	}
}
