package goorm.saerojinro.domain.notification.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum NotificationDomainExceptionCode implements ExceptionCode {
	NOTIFICATION_NOT_FOUND(NOT_FOUND, "Notification not found"),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
