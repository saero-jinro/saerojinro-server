package goorm.saerojinro.common.auth.jwt.exception;

import goorm.saerojinro.common.exception.CustomException;

public class JwtMalformedException extends CustomException {
	public JwtMalformedException() {
		super(JwtExceptionCode.JWT_TOKEN_MALFORMED);
	}
}
