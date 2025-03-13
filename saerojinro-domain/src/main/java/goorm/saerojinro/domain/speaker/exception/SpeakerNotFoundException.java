package goorm.saerojinro.domain.speaker.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.speaker.exception.SpeakerDomainExceptionCode.*;

public class SpeakerNotFoundException extends CustomException {
	public SpeakerNotFoundException() {super(NOT_FOUND_SPEAKER);}
}
