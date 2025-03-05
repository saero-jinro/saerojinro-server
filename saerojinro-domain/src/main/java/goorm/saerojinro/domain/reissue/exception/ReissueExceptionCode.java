package goorm.saerojinro.domain.reissue.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReissueExceptionCode implements ExceptionCode {
	REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "유효한 토큰을 찾을 수 없습니다."),
	REFRESH_TOKEN_MISMATCH(BAD_REQUEST, "리프레시 토큰이 일치하지 않습니다.")
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
