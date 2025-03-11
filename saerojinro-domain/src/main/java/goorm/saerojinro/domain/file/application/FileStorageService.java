package goorm.saerojinro.domain.file.application;

import goorm.saerojinro.domain.file.exception.FileDownloadFailedException;
import goorm.saerojinro.domain.file.exception.FileSaveFailedException;
import goorm.saerojinro.domain.file.exception.FileSizeRetrievalFailedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

	private final RestTemplate restTemplate = new RestTemplate();
	private final String uploadDir = "uploads/";

	public String storeFileFromUri(String fileUri) {
		byte[] fileBytes = restTemplate.getForObject(fileUri, byte[].class);
		if (fileBytes == null) {
			throw new FileDownloadFailedException();
		}

		// 파일명 생성 (현재 시간 기반, 확장자는 추출)
		String extension = extractExtension(fileUri);
		String fileName = "file_" + System.currentTimeMillis() + extension;
		Path path = Paths.get(uploadDir, fileName);
		try {
			Files.createDirectories(path.getParent());
			Files.write(path, fileBytes);
		} catch (IOException e) {
			throw new FileSaveFailedException();
		}
		return path.toString();
	}

	private String extractExtension(String fileUri) {
		int idx = fileUri.lastIndexOf('.');
		return (idx != -1) ? fileUri.substring(idx) : "";
	}

	public Long getFileSize(String storedPath) {
		try {
			return Files.size(Paths.get(storedPath));
		} catch (IOException e) {
			throw new FileSizeRetrievalFailedException();
		}
	}

	public String getFileExtension(String storedPath) {
		int idx = storedPath.lastIndexOf('.');
		return (idx != -1) ? storedPath.substring(idx + 1) : "";
	}
}
