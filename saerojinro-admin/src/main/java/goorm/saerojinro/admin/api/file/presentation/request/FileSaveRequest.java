package goorm.saerojinro.admin.api.file.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record FileSaveRequest(
	@Schema(description = "이미지 URI", example = "강의 썸네일 or 강연자 프로필 URI", requiredMode = REQUIRED)
	@NotNull String uri
) {}
