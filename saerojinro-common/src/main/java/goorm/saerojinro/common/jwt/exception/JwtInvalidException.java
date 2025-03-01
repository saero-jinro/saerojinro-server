package goorm.saerojinro.common.jwt.exception;

import static goorm.saerojinro.common.jwt.exception.JwtExceptionCode.JWT_TOKEN_INVALID;

import goorm.saerojinro.common.exception.CustomException;

public class JwtInvalidException extends CustomException {
	public JwtInvalidException() {
		super(JWT_TOKEN_INVALID);
	}
}
