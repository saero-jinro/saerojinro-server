package goorm.saerojinro.domain.notification.exception;

import goorm.saerojinro.common.exception.CustomException;

public class EmitterNotFoundException extends CustomException {
	public EmitterNotFoundException() {
		super(NotificationDomainExceptionCode.EMITTER_NOT_FOUND);
	}
}
