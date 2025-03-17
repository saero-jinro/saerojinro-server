package goorm.saerojinro.admin.api.user.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record AdminPersistResponse(
	@Schema(description = "관리자 ID", example = "1", requiredMode = REQUIRED)
	Long id
) {
	public static AdminPersistResponse from(User user) {
		return AdminPersistResponse.builder()
			.id(user.getId())
			.build();
	}
}
