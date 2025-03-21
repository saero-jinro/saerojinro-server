package goorm.saerojinro.admin.api.lecture.presentation;

import goorm.saerojinro.admin.api.lecture.application.LectureAdminFacade;
import goorm.saerojinro.admin.api.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.response.LectureCreateResponse;
import goorm.saerojinro.admin.api.lecture.presentation.response.LectureIdNameMappingListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureAdminControllerImpl implements LectureAdminController {
	private final LectureAdminFacade lectureAdminFacade;

	@PostMapping
	public ResponseEntity<LectureCreateResponse> create(@RequestBody LectureCreateRequest request) {
		LectureCreateResponse response = lectureAdminFacade.create(request);
		return ResponseEntity.status(CREATED).body(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id,
									   @RequestBody LectureUpdateRequest request) {
		lectureAdminFacade.update(id, request);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		lectureAdminFacade.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<LectureIdNameMappingListResponse> findAll() {
		LectureIdNameMappingListResponse response = lectureAdminFacade.findAll();
		return ResponseEntity.ok(response);
	}
}
