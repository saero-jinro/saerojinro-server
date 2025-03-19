package goorm.saerojinro.api.file.application;

import goorm.saerojinro.domain.file.application.FileQueryService;
import goorm.saerojinro.domain.file.domain.File;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FileFacade {
	@Value("${aws.bucket-name}")
	private String bucketName;

	private final S3Client s3Client;
	private final FileQueryService fileQueryService;

	public FileResource getFileResource(Long id) throws IOException {
		File file = fileQueryService.getFileById(id);
		return FileResource.from(file, s3Client, bucketName);
	}
}
