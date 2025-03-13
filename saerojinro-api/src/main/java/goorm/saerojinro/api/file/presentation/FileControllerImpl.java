package goorm.saerojinro.api.file.presentation;

import goorm.saerojinro.api.file.application.FileFacade;
import goorm.saerojinro.api.file.application.FileResource;
import goorm.saerojinro.domain.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/files/lecture")
public class FileControllerImpl implements FileController {
	private final FileFacade fileFacade;

	@Override
	@GetMapping("/download")
	public ResponseEntity<Resource> downloadLectureFile(@RequestParam(value = "materialUri") String uri) {
		try {
			FileResource fileResource = fileFacade.getFileResource(uri);
			File file = fileResource.file();
			Resource resource = fileResource.resource();

			return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + file.getLogicalName() + "\"")
				.body(resource);
		} catch (IOException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
