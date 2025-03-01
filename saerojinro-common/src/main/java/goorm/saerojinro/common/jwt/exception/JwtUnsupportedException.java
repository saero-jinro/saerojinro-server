package goorm.saerojinro.common.jwt.exception;

import static goorm.saerojinro.common.jwt.exception.JwtExceptionCode.JWT_TOKEN_UNSUPPORTED;

import goorm.saerojinro.common.exception.CustomException;

public class JwtUnsupportedException extends CustomException {
	public JwtUnsupportedException() {
		super(JWT_TOKEN_UNSUPPORTED);
	}
}
