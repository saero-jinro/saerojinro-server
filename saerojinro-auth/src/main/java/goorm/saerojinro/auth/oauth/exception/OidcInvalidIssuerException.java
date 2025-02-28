package goorm.saerojinro.auth.oauth.exception;

import static goorm.saerojinro.auth.oauth.exception.OidcExceptionCode.OIDC_INVALID_ISSUER;

import goorm.saerojinro.common.exception.CustomException;

public class OidcInvalidIssuerException extends CustomException {
	public OidcInvalidIssuerException() {
		super(OIDC_INVALID_ISSUER);
	}
}
