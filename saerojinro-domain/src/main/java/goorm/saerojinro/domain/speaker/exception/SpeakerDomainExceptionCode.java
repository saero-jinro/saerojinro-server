package goorm.saerojinro.domain.speaker.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum SpeakerDomainExceptionCode implements ExceptionCode {
	NOT_FOUND_SPEAKER(NOT_FOUND, "강연자가 존재하지 않습니다"),
	;
	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
