package goorm.saerojinro.admin.api.file.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record FileSaveRequest(
	@Schema(description = "이미지 URI", example = "https://news.sap.com/korea/files/2023/10/06/AI-in-the-palm-of-your-hand-getty.jpg", requiredMode = REQUIRED)
	@NotNull String uri
) {}
