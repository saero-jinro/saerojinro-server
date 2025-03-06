package goorm.saerojinro.admin.api.presentation.request;

import goorm.saerojinro.common.domain.BaseRole;
import lombok.Builder;

@Builder
public record RoleUpdateRequest(
	BaseRole permission
) {

}
