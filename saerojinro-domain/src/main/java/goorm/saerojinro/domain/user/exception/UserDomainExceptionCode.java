package goorm.saerojinro.domain.user.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserDomainExceptionCode implements ExceptionCode {
	USER_NOT_FOUND(NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
	INVALID_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	INVALID_USER(BAD_REQUEST, "사용자가 일치하지 않습니다"),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "유효한 인증 정보가 없습니다."),
	PROVIDER_NOT_FOUND(BAD_REQUEST, "올바르지 않은 제공자입니다.")
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
