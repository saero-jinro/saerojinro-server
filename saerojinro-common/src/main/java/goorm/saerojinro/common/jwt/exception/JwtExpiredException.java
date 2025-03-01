package goorm.saerojinro.common.jwt.exception;

import static goorm.saerojinro.common.jwt.exception.JwtExceptionCode.JWT_TOKEN_EXPIRED;

import goorm.saerojinro.common.exception.CustomException;

public class JwtExpiredException extends CustomException {
	public JwtExpiredException() {
		super(JWT_TOKEN_EXPIRED);
	}
}
