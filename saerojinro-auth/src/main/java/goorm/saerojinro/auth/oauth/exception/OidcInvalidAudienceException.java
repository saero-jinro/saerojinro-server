package goorm.saerojinro.auth.oauth.exception;

import static goorm.saerojinro.auth.oauth.exception.OidcExceptionCode.OIDC_INVALID_AUDIENCE;

import goorm.saerojinro.common.exception.CustomException;

public class OidcInvalidAudienceException extends CustomException {
	public OidcInvalidAudienceException() {
		super(OIDC_INVALID_AUDIENCE);
	}
}
