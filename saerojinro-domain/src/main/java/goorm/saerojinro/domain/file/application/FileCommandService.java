package goorm.saerojinro.domain.file.application;

import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.file.domain.FileRepository;
import goorm.saerojinro.domain.file.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileCommandService {
	private final FileRepository fileRepository;

	public File save(String logicalName, String physicalPath, Long fileSize, String extension) {
		File createdFile = File.create(logicalName, physicalPath, fileSize, extension);
		return fileRepository.save(createdFile);
	}

	public void updateFilePhysicalPath(Long fileId, String newPhysicalPath) {
		File file = fileRepository.findById(fileId).orElseThrow(FileNotFoundException::new);
		file.updatePhysicalPath(newPhysicalPath);
		fileRepository.save(file);
	}
}
