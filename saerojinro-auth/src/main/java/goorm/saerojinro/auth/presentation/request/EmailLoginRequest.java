package goorm.saerojinro.auth.presentation.request;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EmailLoginRequest(
	@Schema(description = "이메일", example = "alswns11346@kgu.ac.kr", requiredMode = REQUIRED)
	@NotNull
	String email,

	@Schema(description = "비밀번호", example = "password1234!", requiredMode = REQUIRED)
	@NotNull
	String password
) {
}
