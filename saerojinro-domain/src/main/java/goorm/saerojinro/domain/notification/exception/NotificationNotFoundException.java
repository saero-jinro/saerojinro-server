package goorm.saerojinro.domain.notification.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.notification.exception.NotificationDomainExceptionCode.NOTIFICATION_NOT_FOUND;

public class NotificationNotFoundException extends CustomException {
	public NotificationNotFoundException() {
		super(NOTIFICATION_NOT_FOUND);
	}
}
