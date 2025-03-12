package goorm.saerojinro.domain.file.application;

import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.file.domain.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileCommandService {
	private final FileRepository fileRepository;

	public File save(String logicalName, String storedPath, Long fileSize, String extension) {
		File createdFile = File.create(logicalName, storedPath, fileSize, extension);
		return fileRepository.save(createdFile);
	}
}
