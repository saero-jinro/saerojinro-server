package goorm.saerojinro.common.auth.jwt.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExceptionCode implements ExceptionCode {
	JWT_TOKEN_EXPIRED(UNAUTHORIZED, "JWT 토큰이 만료되었습니다."),
	JWT_TOKEN_UNSUPPORTED(BAD_REQUEST, "지원되지 않는 JWT 토큰 형식입니다."),
	JWT_TOKEN_MALFORMED(BAD_REQUEST, "잘못된 형식의 JWT 토큰입니다."),
	JWT_SIGNATURE_INVALID(UNAUTHORIZED, "JWT 토큰의 서명이 유효하지 않습니다."),
	JWT_TOKEN_INVALID(BAD_REQUEST, "JWT 토큰이 유효하지 않습니다.");

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
