package goorm.saerojinro.admin.api.file.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record FileSaveRequest(
	@Schema(description = "이미지 URI", example = "https://dyns.co.kr/wp-content/uploads/2024/04/placeholder-304.png", requiredMode = REQUIRED)
	@NotNull String uri
) {}
