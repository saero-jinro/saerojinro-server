package goorm.saerojinro.auth.social.exception;

import static goorm.saerojinro.auth.social.exception.OidcExceptionCode.OIDC_INVALID_ID_TOKEN;

import goorm.saerojinro.common.exception.CustomException;

public class OidcInvalidIdTokenException extends CustomException {
	public OidcInvalidIdTokenException() {
		super(OIDC_INVALID_ID_TOKEN);
	}
}
