package goorm.saerojinro.admin.file.response;

import goorm.saerojinro.domain.file.domain.File;
import lombok.Builder;

@Builder
public record FileReadResponse(
	Long id,
	String logicalName,
	String physicalPath,
	Long fileSize,
	String extension
) {
	public static FileReadResponse from(File file) {
		return FileReadResponse.builder()
			.id(file.getId())
			.logicalName(file.getLogicalName())
			.physicalPath(file.getPhysicalPath())
			.fileSize(file.getFileSize())
			.extension(file.getExtension())
			.build();
	}
}
