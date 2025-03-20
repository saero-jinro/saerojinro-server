package goorm.saerojinro.api.file.application;

import goorm.saerojinro.domain.file.domain.File;
import lombok.Builder;
import org.springframework.core.io.ByteArrayResource;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.IOException;

@Builder
public record FileResource(
	File file,
	ByteArrayResource resource
) {
	public static FileResource from(File file, S3Client s3Client, String bucketName) throws IOException {
		GetObjectRequest getObjectRequest = GetObjectRequest.builder()
			.bucket(bucketName)
			.key(file.getPhysicalPath())
			.build();

		try (ResponseInputStream<GetObjectResponse> s3Stream = s3Client.getObject(getObjectRequest)) {
			byte[] data = s3Stream.readAllBytes();
			return FileResource.builder()
				.file(file)
				.resource(new ByteArrayResource(data))
				.build();
		}
	}
}