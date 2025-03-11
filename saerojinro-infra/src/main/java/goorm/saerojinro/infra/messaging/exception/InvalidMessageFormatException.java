package goorm.saerojinro.infra.messaging.exception;

import static goorm.saerojinro.infra.messaging.exception.RedisMessagingExceptionCode.INVALID_MESSAGE_FORMAT;

import goorm.saerojinro.common.exception.CustomException;

public class InvalidMessageFormatException extends CustomException {
	public InvalidMessageFormatException() {
		super(INVALID_MESSAGE_FORMAT);
	}
}
