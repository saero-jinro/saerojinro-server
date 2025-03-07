package goorm.saerojinro.admin.notification.exception;

import goorm.saerojinro.common.exception.CustomException;

public class EmitterNotFoundException extends CustomException {
	public EmitterNotFoundException() {
		super(NotificationApiExceptionCode.EMITTER_NOT_FOUND);
	}
}
