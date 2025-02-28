package goorm.saerojinro.domain.user.exception;

import static goorm.saerojinro.domain.user.exception.UserDomainExceptionCode.UNAUTHORIZED;

import goorm.saerojinro.common.exception.CustomException;

public class UnauthorizedException extends CustomException {
	public UnauthorizedException() {
		super(UNAUTHORIZED);
	}
}
