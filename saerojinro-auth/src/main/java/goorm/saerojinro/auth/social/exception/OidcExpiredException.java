package goorm.saerojinro.auth.social.exception;

import static goorm.saerojinro.auth.social.exception.OidcExceptionCode.OIDC_EXPIRED;

import goorm.saerojinro.common.exception.CustomException;

public class OidcExpiredException extends CustomException {
	public OidcExpiredException() {
		super(OIDC_EXPIRED);
	}
}
