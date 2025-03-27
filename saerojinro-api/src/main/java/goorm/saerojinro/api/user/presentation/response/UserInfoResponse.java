package goorm.saerojinro.api.user.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record UserInfoResponse(
	@Schema(description = "사용지 이름", example = "박민준", requiredMode = REQUIRED)
	String name,

	@Schema(description = "사용자 이메일", example = "alswns11346@kgu.ac.kr", requiredMode = REQUIRED)
	String email,

	@Schema(description = "사용자 프로필 이미지 URL", example = "https://k.kakaocdn.net/dn/ccql3q/btsMYyItVnS/jqEZLlw30FznN72RlQnH2k/img_110x110.jpg", requiredMode = REQUIRED)
	String profileImage
) {
	public static UserInfoResponse from(User user) {
		return UserInfoResponse.builder()
			.name(user.getName())
			.email(user.getEmail())
			.profileImage(user.getProfileImage())
			.build();
	}
}
