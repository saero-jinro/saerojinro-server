package goorm.saerojinro.domain.user.exception;

import static goorm.saerojinro.domain.user.exception.UserDomainExceptionCode.INVALID_USER;

import goorm.saerojinro.common.exception.CustomException;

public class InvalidUserException extends CustomException {
	public InvalidUserException() {
		super(INVALID_USER);
	}
}
