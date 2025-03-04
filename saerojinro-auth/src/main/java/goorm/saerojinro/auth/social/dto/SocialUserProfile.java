package goorm.saerojinro.auth.social.dto;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;

import lombok.Builder;

@Builder
public record SocialUserProfile(
	String identifier,
	String name,
	String email,
	String profileImage
) {
	public static SocialUserProfile from(OidcIdToken oidcIdToken) {
		return SocialUserProfile.builder()
			.identifier(oidcIdToken.getSubject())
			.name(oidcIdToken.getNickName())
			.email(oidcIdToken.getEmail())
			.profileImage(oidcIdToken.getPicture())
			.build();
	}
}
