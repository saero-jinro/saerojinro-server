package goorm.saerojinro.admin.api.file.presentation.response;

import goorm.saerojinro.domain.file.domain.File;
import lombok.Builder;

@Builder
public record FileSaveResponse(
	Long id
) {
	public static FileSaveResponse from(File file) {
		return FileSaveResponse.builder()
			.id(file.getId())
			.build();
	}
}
