package goorm.saerojinro.auth.oauth.exception;

import static goorm.saerojinro.auth.oauth.exception.OidcExceptionCode.OIDC_INVALID_ID_TOKEN;

import goorm.saerojinro.common.exception.CustomException;

public class OidcInvalidIdTokenException extends CustomException {
	public OidcInvalidIdTokenException() {
		super(OIDC_INVALID_ID_TOKEN);
	}
}
