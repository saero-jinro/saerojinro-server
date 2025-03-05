package goorm.saerojinro.domain.lecture.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.lecture.exception.LectureDomainExceptionCode.*;

public class SpeakerMissmatchException extends CustomException {
	public SpeakerMissmatchException() {
		super(SPEAKER_MISS_MATCH);
	}
}
