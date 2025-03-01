package goorm.saerojinro.admin.presentation.response;

import goorm.saerojinro.domain.user.domain.User;
import lombok.Builder;

@Builder
public record AdminPersistResponse(
	Long id
) {
	public static AdminPersistResponse from(User user) {
		return AdminPersistResponse.builder()
			.id(user.getId())
			.build();
	}
}
