package goorm.saerojinro.common.exception;

import static goorm.saerojinro.common.exception.GlobalExceptionCode.PROVIDER_NOT_FOUND;

public class ProviderNotFoundException extends CustomException {
	public ProviderNotFoundException() {
		super(PROVIDER_NOT_FOUND);
	}
}
