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

	public FileResource getFileResource(String uri) throws IOException {
		File file = fileQueryService.getFileByUri(uri);

		return FileResource.from(file);
	}
}
