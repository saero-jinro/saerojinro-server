package goorm.saerojinro.admin.lecture.presentation;

import goorm.saerojinro.admin.lecture.application.AdminLectureFacade;
import goorm.saerojinro.admin.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.lecture.presentation.response.LectureCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/lectures")
@RequiredArgsConstructor
public class AdminLectureControllerImpl implements AdminLectureController {
	private final AdminLectureFacade adminLectureFacade;

	@PostMapping
	public ResponseEntity<LectureCreateResponse> create(@RequestBody LectureCreateRequest request) {
		return ResponseEntity.ok(adminLectureFacade.create(request));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id,
									   @RequestBody LectureUpdateRequest request) {
		adminLectureFacade.update(id, request);
		return ResponseEntity.noContent().build();
	}


	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		adminLectureFacade.delete(id);
		return ResponseEntity.noContent().build();
	}
}
