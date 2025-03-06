package goorm.saerojinro.common.domain.blacklist.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlackListExceptionCode implements ExceptionCode {
	BLACK_LISTED_TOKEN(UNAUTHORIZED, "이미 로그아웃되었거나, 유효하지 않은 요청입니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
