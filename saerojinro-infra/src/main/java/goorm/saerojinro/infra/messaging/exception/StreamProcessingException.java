package goorm.saerojinro.infra.messaging.exception;

import static goorm.saerojinro.infra.messaging.exception.RedisMessagingExceptionCode.STREAM_PROCESSING_ERROR;

import goorm.saerojinro.common.exception.CustomException;

public class StreamProcessingException extends CustomException {
	public StreamProcessingException() {
		super(STREAM_PROCESSING_ERROR);
	}
}
