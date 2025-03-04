package goorm.saerojinro.domain.user.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.user.exception.UserDomainExceptionCode.INVALID_USER_ROLE;

public class InvalidUserRoleException extends CustomException {
	public InvalidUserRoleException() {
		super(INVALID_USER_ROLE);
	}
}
