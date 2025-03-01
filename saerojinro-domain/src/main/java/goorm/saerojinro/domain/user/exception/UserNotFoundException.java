package goorm.saerojinro.domain.user.exception;

import static goorm.saerojinro.domain.user.exception.UserDomainExceptionCode.USER_NOT_FOUND;

import goorm.saerojinro.common.exception.CustomException;

public class UserNotFoundException extends CustomException {
	public UserNotFoundException() {
		super(USER_NOT_FOUND);
	}
}
