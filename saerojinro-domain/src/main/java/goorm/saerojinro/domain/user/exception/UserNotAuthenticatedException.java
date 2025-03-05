package goorm.saerojinro.domain.user.exception;

import static goorm.saerojinro.domain.user.exception.UserDomainExceptionCode.USER_NOT_AUTHENTICATED;

import goorm.saerojinro.common.exception.CustomException;

public class UserNotAuthenticatedException extends CustomException {
	public UserNotAuthenticatedException() {
		super(USER_NOT_AUTHENTICATED);
	}
}
