package goorm.saerojinro.common.auth.jwt.exception;

import goorm.saerojinro.common.exception.CustomException;

public class JwtInvalidException extends CustomException {
	public JwtInvalidException() {
		super(JwtExceptionCode.JWT_TOKEN_INVALID);
	}
}
