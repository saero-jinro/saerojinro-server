package goorm.saerojinro.auth.social.exception;

import static goorm.saerojinro.auth.social.exception.OidcExceptionCode.OIDC_INVALID_ISSUER;

import goorm.saerojinro.common.exception.CustomException;

public class OidcInvalidIssuerException extends CustomException {
	public OidcInvalidIssuerException() {
		super(OIDC_INVALID_ISSUER);
	}
}
