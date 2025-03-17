package goorm.saerojinro.domain.file.application;

import goorm.saerojinro.domain.file.exception.FileDownloadFailedException;
import goorm.saerojinro.domain.file.exception.FileSaveFailedException;
import goorm.saerojinro.domain.file.exception.FileSizeRetrievalFailedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

	private final RestTemplate restTemplate;

	public FileStorageService() {
		this.restTemplate = new RestTemplate();
	}

	public String storeFileFromUri(String fileUri, String tempDir) {
		String encodedUrl = encodeUrl(fileUri);
		byte[] fileBytes = restTemplate.getForObject(encodedUrl, byte[].class);
		if (fileBytes == null) {
			throw new FileDownloadFailedException();
		}
		String extension = extractExtension(fileUri);
		String fileName = System.currentTimeMillis() + extension;

		Path path = Paths.get(tempDir, fileName);
		try {
			Files.createDirectories(path.getParent());
			Files.write(path, fileBytes);
		} catch (IOException e) {
			throw new FileSaveFailedException();
		}
		return path.toString();
	}

	public String storeFile(MultipartFile multipartFile, String tempDir) {
		String fileName = multipartFile.getOriginalFilename();
		Path path = Paths.get(tempDir, fileName);
		try {
			Files.createDirectories(path.getParent());
			multipartFile.transferTo(path);
		} catch (IOException e) {
			throw new FileSaveFailedException();
		}
		return path.toString();
	}

	public String moveFileDir(String tempPath, Long lectureId, String folderName) {
		Path source = Paths.get(tempPath);
		String fileName = source.getFileName().toString();

		Path target = Paths.get("uploads/" + lectureId, folderName, fileName);
		try {
			Files.createDirectories(target.getParent());
			Files.move(source, target);
		} catch (IOException e) {
			throw new FileSaveFailedException();
		}
		return target.toString();
	}

	private String encodeUrl(String fileUri) {
		try {
			String encodedUrl = UriComponentsBuilder.fromUriString(fileUri)
				.encode()
				.toUriString();
			return encodedUrl.replace("(", "%28").replace(")", "%29");
		} catch (Exception e) {
			return fileUri;
		}
	}

	private String extractExtension(String fileUri) {
		int queryIdx = fileUri.indexOf('?');
		String uriWithoutQuery = (queryIdx != -1)
			? fileUri.substring(0, queryIdx)
			: fileUri;
		int idx = uriWithoutQuery.lastIndexOf('.');
		return (idx != -1) ? uriWithoutQuery.substring(idx) : "";
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
