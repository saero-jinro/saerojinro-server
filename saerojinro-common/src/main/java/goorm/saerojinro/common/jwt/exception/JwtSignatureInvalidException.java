package goorm.saerojinro.common.jwt.exception;

import static goorm.saerojinro.common.jwt.exception.JwtExceptionCode.JWT_SIGNATURE_INVALID;

import goorm.saerojinro.common.exception.CustomException;

public class JwtSignatureInvalidException extends CustomException {
	public JwtSignatureInvalidException() {
		super(JWT_SIGNATURE_INVALID);
	}
}
