package goorm.saerojinro.api.user.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.common.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record UserUpdateRequest(
	@Schema(description = "이름", example = "박민준", requiredMode = REQUIRED)
	String name,

	@Schema(description = "이메일", example = "qkralswnsWkd@kyonggi.ac.kr", requiredMode = REQUIRED)
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "유효한 이메일 형식이 아닙니다.")
	String email,

	@Schema(description = "관심사", example = "BACKEND", requiredMode = REQUIRED)
	Category interest
) {
}
