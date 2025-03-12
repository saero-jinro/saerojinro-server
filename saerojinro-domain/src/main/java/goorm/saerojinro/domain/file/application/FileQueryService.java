package goorm.saerojinro.domain.file.application;

import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.file.domain.FileRepository;
import goorm.saerojinro.domain.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileQueryService {
	private final FileRepository fileRepository;

	public File getFileById(Long id) {
		return fileRepository.findById(id).orElseThrow(FileNotFoundException::new);
	}

	public File getFileByUri(String uri) {
		return fileRepository.findByUri(uri).orElseThrow(FileNotFoundException::new);
	}
}
