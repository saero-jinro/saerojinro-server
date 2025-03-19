package goorm.saerojinro.domain.file.application;

import goorm.saerojinro.domain.file.exception.FileDownloadFailedException;
import goorm.saerojinro.domain.file.exception.FileSaveFailedException;
import goorm.saerojinro.domain.file.exception.FileSizeRetrievalFailedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
	private final S3Client s3Client;
	private final String bucketName = "saerojinro-bucket";
	private final RestTemplate restTemplate;

	public FileStorageService() {
		this.s3Client = S3Client.builder()
			.region(Region.AP_NORTHEAST_2)
			.credentialsProvider(DefaultCredentialsProvider.create())
			.build();
		this.restTemplate = new RestTemplate();
	}

	public String storeFileFromUri(String fileUri, String s3Folder) {
		String encodedUrl = encodeUrl(fileUri);
		byte[] fileBytes = restTemplate.getForObject(encodedUrl, byte[].class);
		if (fileBytes == null) {
			throw new FileDownloadFailedException();
		}
		String extension = extractExtension(fileUri);
		String key = s3Folder + System.currentTimeMillis() + extension;

		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
			.bucket(bucketName)
			.key(key)
			.acl("public-read")
			.build();
		s3Client.putObject(putObjectRequest, RequestBody.fromBytes(fileBytes));
		return key;
	}

	public String storeFile(MultipartFile multipartFile, String s3Folder) {
		String fileName = multipartFile.getOriginalFilename();
		String key = s3Folder + System.currentTimeMillis() + "_" + fileName;
		try {
			PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.acl("public-read")
				.build();
			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
		} catch (IOException e) {
			throw new FileSaveFailedException();
		}
		return key;
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
