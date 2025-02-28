package goorm.saerojinro.common.auth.jwt.exception;

import goorm.saerojinro.common.exception.CustomException;

public class JwtSignatureInvalidException extends CustomException {
	public JwtSignatureInvalidException() {
		super(JwtExceptionCode.JWT_SIGNATURE_INVALID);
	}
}
