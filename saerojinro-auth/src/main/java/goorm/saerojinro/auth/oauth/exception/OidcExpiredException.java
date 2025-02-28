package goorm.saerojinro.auth.oauth.exception;

import static goorm.saerojinro.auth.oauth.exception.OidcExceptionCode.OIDC_EXPIRED;

import goorm.saerojinro.common.exception.CustomException;

public class OidcExpiredException extends CustomException {
	public OidcExpiredException() {
		super(OIDC_EXPIRED);
	}
}
