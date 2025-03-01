package goorm.saerojinro.admin.presentation;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import goorm.saerojinro.admin.api.AdminFacade;
import goorm.saerojinro.admin.presentation.request.AdminCreateRequest;
import goorm.saerojinro.admin.presentation.response.AdminPersistResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminControllerImpl implements AdminController {
	private final AdminFacade adminFacade;

	@Override
	@PostMapping
	public ResponseEntity<AdminPersistResponse> createAdmin(AdminCreateRequest request) {
		AdminPersistResponse response = adminFacade.createAdmin(request);
		return ResponseEntity.status(CREATED).body(response);
	}
}
