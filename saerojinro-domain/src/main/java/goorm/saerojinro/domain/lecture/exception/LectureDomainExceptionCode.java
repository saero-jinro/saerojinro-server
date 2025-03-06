package goorm.saerojinro.domain.lecture.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum LectureDomainExceptionCode implements ExceptionCode {
	LECTURE_NOT_FOUND(NOT_FOUND, "해당 강의를 찾을 수 없습니다."),
	LECTURE_UPDATE_FORBIDDEN(FORBIDDEN, "강의를 수정할 권한이 없습니다."),
	LECTURE_DELETE_FORBIDDEN(FORBIDDEN, "강의를 삭제할 권한이 없습니다.");
	;

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
