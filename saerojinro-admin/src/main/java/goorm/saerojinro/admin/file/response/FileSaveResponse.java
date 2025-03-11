package goorm.saerojinro.admin.file.response;

import goorm.saerojinro.domain.file.domain.File;
import lombok.Builder;

@Builder
public record FileSaveResponse(
	Long id,
	String physicalPath
) {
	public static FileSaveResponse from(File file) {
		return FileSaveResponse.builder()
			.id(file.getId())
			.physicalPath(file.getPhysicalPath())
			.build();
	}
}
