package goorm.saerojinro.common.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;

public record ExceptionResponse(
	@JsonIgnore HttpStatus status,
	String code,
	@JsonIgnore String message
) {

	@Builder
	public ExceptionResponse {
	}

	public static ExceptionResponse from(CustomException exception) {
		return builder()
			.status(exception.getCode().getStatus())
			.code(exception.getCode().getCode())
			.build();
	}

	public static ExceptionResponse from(ExceptionCode code) {
		return builder()
			.status(code.getStatus())
			.code(code.getCode())
			.build();
	}

	public static ExceptionResponse of(HttpStatus status, String code, String message) {
		return builder()
			.status(status)
			.code(code)
			.message(message)
			.build();
	}
}
