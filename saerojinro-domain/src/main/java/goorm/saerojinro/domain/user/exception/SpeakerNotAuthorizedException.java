package goorm.saerojinro.domain.user.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.user.exception.UserDomainExceptionCode.*;

public class SpeakerNotAuthorizedException extends CustomException {
	public SpeakerNotAuthorizedException() {
		super(SPEAKER_NOT_AUTHORIZED);
	}
}
