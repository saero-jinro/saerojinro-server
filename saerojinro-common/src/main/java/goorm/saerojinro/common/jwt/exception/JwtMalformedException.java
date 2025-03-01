package goorm.saerojinro.common.jwt.exception;

import static goorm.saerojinro.common.jwt.exception.JwtExceptionCode.JWT_TOKEN_MALFORMED;

import goorm.saerojinro.common.exception.CustomException;

public class JwtMalformedException extends CustomException {
	public JwtMalformedException() {
		super(JWT_TOKEN_MALFORMED);
	}
}
