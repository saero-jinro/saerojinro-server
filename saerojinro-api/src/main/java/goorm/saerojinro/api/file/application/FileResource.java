package goorm.saerojinro.api.file.application;

import goorm.saerojinro.domain.file.domain.File;
import lombok.Builder;
import org.springframework.core.io.ByteArrayResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Builder
public record FileResource(
	File file,
	ByteArrayResource resource
) {
	public static FileResource from(File file) throws IOException {
		Path filePath = Paths.get(file.getPhysicalPath());
		byte[] data = Files.readAllBytes(filePath);

		return FileResource.builder()
			.file(file)
			.resource(new ByteArrayResource(data))
			.build();
	}
}