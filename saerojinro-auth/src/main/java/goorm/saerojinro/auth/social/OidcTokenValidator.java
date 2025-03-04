package goorm.saerojinro.auth.social;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import goorm.saerojinro.auth.social.exception.OidcExpiredException;
import goorm.saerojinro.auth.social.exception.OidcInvalidAudienceException;
import goorm.saerojinro.auth.social.exception.OidcInvalidIssuerException;
import goorm.saerojinro.common.domain.Provider;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class OidcTokenValidator {
	protected abstract String getIssuer();
	protected abstract String getJwkSetUri();
	protected abstract String getClientId();
	protected abstract Provider getProvider();

	private JwtDecoder buildDecoder(String jwkSetUri) {
		return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
	}

	public OidcIdToken validateAndDecodeIdToken(String idToken) {
		JwtDecoder jwtDecoder = buildDecoder(getJwkSetUri());
		Jwt jwt = jwtDecoder.decode(idToken);
		OidcIdToken oidcIdToken = getOidcIdToken(jwt);

		validateIssuer(oidcIdToken.getIssuer().toString());
		validateAudience(oidcIdToken.getAudience());
		validateExpiration(Objects.requireNonNull(oidcIdToken.getExpiresAt()));

		return oidcIdToken;
	}

	private void validateIssuer(String issuer) {
		if (!getIssuer().equals(issuer)) {
			throw new OidcInvalidIssuerException();
		}
	}

	private void validateAudience(List<String> audience) {
		if (audience == null || !audience.contains(getClientId())) {
			throw new OidcInvalidAudienceException();
		}
	}

	private void validateExpiration(Instant expiration) {
		if (expiration.isBefore(Instant.now())) {
			throw new OidcExpiredException();
		}
	}

	private OidcIdToken getOidcIdToken(Jwt jwt) {
		return new OidcIdToken(
			jwt.getTokenValue(), jwt.getIssuedAt(), jwt.getExpiresAt(), jwt.getClaims());
	}
}
