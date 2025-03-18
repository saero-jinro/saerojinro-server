package goorm.saerojinro.api.file.application;

import goorm.saerojinro.domain.file.application.FileQueryService;
import goorm.saerojinro.domain.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FileFacade {
	private final FileQueryService fileQueryService;

	public FileResource getFileResource(Long id) throws IOException {
		File file = fileQueryService.getFileById(id);
		return FileResource.from(file);
	}
}
