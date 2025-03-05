package goorm.saerojinro.domain.user.exception;

import org.springframework.http.HttpStatus;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum UserDomainExceptionCode implements ExceptionCode {
	USER_NOT_FOUND(NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
	INVALID_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
	INVALID_USER_ROLE(FORBIDDEN, "강연자가 아닌 유저입니다."),
	USER_NOT_AUTHENTICATED(UNAUTHORIZED, "회원 인증에 실패하였습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
