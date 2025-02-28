package goorm.saerojinro.common.auth.jwt.exception;

import goorm.saerojinro.common.exception.CustomException;

public class JwtUnsupportedException extends CustomException {
	public JwtUnsupportedException() {
		super(JwtExceptionCode.JWT_TOKEN_UNSUPPORTED);
	}
}
