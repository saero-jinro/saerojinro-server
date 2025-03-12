package goorm.saerojinro.admin.file;

import goorm.saerojinro.admin.file.application.FileAdminFacade;
import goorm.saerojinro.admin.file.request.FileSaveRequest;
import goorm.saerojinro.admin.file.response.FileSaveResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/files")
@RequiredArgsConstructor
public class FileAdminControllerImpl implements FileAdminController {
	private final FileAdminFacade fileAdminFacade;

	@Override
	@PostMapping
	public ResponseEntity<FileSaveResponse> create(@RequestBody FileSaveRequest request) {
		FileSaveResponse response = fileAdminFacade.saveFile(request);
		return ResponseEntity.ok(response);
	}
}
