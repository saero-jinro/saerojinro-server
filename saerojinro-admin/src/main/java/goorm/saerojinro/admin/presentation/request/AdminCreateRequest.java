package goorm.saerojinro.admin.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record AdminCreateRequest(
	@Schema(description = "비밀번호", example = "password1234!", requiredMode = REQUIRED)
	@Pattern(
		regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]{8,15}$",
		message = "비밀번호는 영문, 숫자, 특수문자를 포함하여 8~15자리여야 합니다."
	)
	@NotNull
	String password,

	@Schema(description = "이름", example = "박민준", requiredMode = REQUIRED)
	@NotNull
	String name,

	@Schema(description = "이메일", example = "qkralswnsWkd@kyonggi.ac.kr", requiredMode = REQUIRED)
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "올바른 이메일 형식이어야 합니다.")
	String email
) {
}
