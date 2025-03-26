package goorm.saerojinro.api.file.presentation;

import goorm.saerojinro.api.file.application.FileFacade;
import goorm.saerojinro.api.file.application.FileResource;
import goorm.saerojinro.domain.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/files")
public class FileControllerImpl implements FileController {
	private final FileFacade fileFacade;

	@Override
	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> downloadLectureFile(@PathVariable Long id) {
		try {
			FileResource fileResource = fileFacade.getFileResource(id);
			File file = fileResource.file();

			return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + file.getLogicalName() + "\"")
				.body(fileResource.resource());
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
