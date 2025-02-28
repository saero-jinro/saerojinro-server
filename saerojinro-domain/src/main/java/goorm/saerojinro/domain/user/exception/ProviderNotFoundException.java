package goorm.saerojinro.domain.user.exception;

import static goorm.saerojinro.domain.user.exception.UserDomainExceptionCode.PROVIDER_NOT_FOUND;

import goorm.saerojinro.common.exception.CustomException;

public class ProviderNotFoundException extends CustomException {
	public ProviderNotFoundException() {
		super(PROVIDER_NOT_FOUND);
	}
}
