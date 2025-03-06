package goorm.saerojinro.auth.api.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record ReissueRequest(
	@Schema(description = "재발행 토큰", example = "REFRESH_TOKEN", requiredMode = REQUIRED)
	@NotBlank
	String refreshToken
) {
}
