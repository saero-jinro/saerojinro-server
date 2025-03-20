package goorm.saerojinro.domain.file.application;

import goorm.saerojinro.domain.file.exception.FileDownloadFailedException;
import goorm.saerojinro.domain.file.exception.FileSaveFailedException;
import goorm.saerojinro.domain.file.exception.FileSizeRetrievalFailedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileStorageService {
	@Value("${aws.bucket-name}")
	private String bucketName;

	private final S3Client s3Client;
	private final RestTemplate restTemplate = new RestTemplate();

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
				.build();
			s3Client.putObject(putObjectRequest, RequestBody.fromBytes(multipartFile.getBytes()));
		} catch (IOException e) {
			throw new FileSaveFailedException();
		}
		return key;
	}
	public String moveFileDir(String oldKey, Long lectureId, String folderName) {
		String fileName = oldKey.substring(oldKey.lastIndexOf('/') + 1);
		String newKey = "uploads/" + lectureId + "/" + folderName + "/" + fileName;

		CopyObjectRequest copyRequest = CopyObjectRequest.builder()
			.sourceBucket(bucketName)
			.sourceKey(oldKey)
			.destinationBucket(bucketName)
			.destinationKey(newKey)
			.build();
		s3Client.copyObject(copyRequest);

		DeleteObjectRequest delRequest = DeleteObjectRequest.builder()
			.bucket(bucketName)
			.key(oldKey)
			.build();
		s3Client.deleteObject(delRequest);
		return newKey;
	}

	public Long getFileSize(String key) {
		try {
			HeadObjectRequest headRequest = HeadObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.build();
			HeadObjectResponse headResponse = s3Client.headObject(headRequest);
			return headResponse.contentLength();
		} catch (Exception e) {
			throw new FileSizeRetrievalFailedException();
		}
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

	public String getFileExtension(String storedPath) {
		int idx = storedPath.lastIndexOf('.');
		return (idx != -1) ? storedPath.substring(idx + 1) : "";
	}
}
