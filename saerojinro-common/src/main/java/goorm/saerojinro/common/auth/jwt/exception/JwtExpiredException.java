package goorm.saerojinro.common.auth.jwt.exception;

import static goorm.saerojinro.common.auth.jwt.exception.JwtExceptionCode.JWT_TOKEN_EXPIRED;

import goorm.saerojinro.common.exception.CustomException;

public class JwtExpiredException extends CustomException {
	public JwtExpiredException() {
		super(JWT_TOKEN_EXPIRED);
	}
}
