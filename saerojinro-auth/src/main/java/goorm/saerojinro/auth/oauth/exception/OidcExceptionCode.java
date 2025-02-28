package goorm.saerojinro.auth.oauth.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OidcExceptionCode implements ExceptionCode {
	OIDC_INVALID_ISSUER(UNAUTHORIZED, "ID 토큰의 발급자가 유효하지 않습니다."),
	OIDC_INVALID_AUDIENCE(UNAUTHORIZED, "ID 토큰의 대상이 유효하지 않습니다."),
	OIDC_EXPIRED(UNAUTHORIZED, "ID 토큰이 만료되었습니다."),
	OIDC_INVALID_ID_TOKEN(BAD_REQUEST, "ID 토큰 검증에 실패하였습니다."),
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
