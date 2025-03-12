package goorm.saerojinro.admin.api.file.presentation;

import goorm.saerojinro.admin.api.file.application.FileAdminFacade;
import goorm.saerojinro.admin.api.file.presentation.request.FileSaveRequest;
import goorm.saerojinro.admin.api.file.presentation.response.FileSaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileAdminControllerImpl implements FileAdminController {
	private final FileAdminFacade fileAdminFacade;

	@PostMapping("/lecture/thumbnail")
	public ResponseEntity<FileSaveResponse> saveLecturePhoto(FileSaveRequest request) {
		FileSaveResponse response = fileAdminFacade.saveLecturePhoto(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/speaker")
	public ResponseEntity<FileSaveResponse> saveSpeakerPhoto(FileSaveRequest request) {
		FileSaveResponse response = fileAdminFacade.saveSpeakerPhoto(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/lecture/materials", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<FileSaveResponse> uploadLectureMaterials(@RequestPart(value = "file") MultipartFile file) {
		FileSaveResponse response = fileAdminFacade.saveLectureMaterials(file);
		return ResponseEntity.ok(response);
	}
}
