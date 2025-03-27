package goorm.saerojinro.api.user.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserInfoResponse(
	@Schema(description = "사용자 이름", example = "박민준", requiredMode = REQUIRED)
	String name,

	@Schema(description = "사용자 이메일", example = "alswns11346@kgu.ac.kr", requiredMode = REQUIRED)
	String email
) {
	public static UserInfoResponse from(User user) {
		return UserInfoResponse.builder()
			.name(user.getName())
			.email(user.getEmail())
			.build();
	}
}
