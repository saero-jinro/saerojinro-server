package goorm.saerojinro.domain.user.exception;

import static goorm.saerojinro.domain.user.exception.UserDomainExceptionCode.INVALID_PASSWORD;

import goorm.saerojinro.common.exception.CustomException;

public class InvalidPasswordException extends CustomException {
	public InvalidPasswordException() {
		super(INVALID_PASSWORD);
	}
}
