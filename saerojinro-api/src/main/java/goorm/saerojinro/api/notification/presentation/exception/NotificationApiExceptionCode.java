package goorm.saerojinro.api.notification.presentation.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum NotificationApiExceptionCode implements ExceptionCode {
	EMITTER_NOT_FOUND(NOT_FOUND, "No Such Emitter With UserId"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
