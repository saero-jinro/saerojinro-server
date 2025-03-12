package goorm.saerojinro.domain.file.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum FileDomainExceptionCode implements ExceptionCode {
	FILE_NOT_FOUND(NOT_FOUND, "해당 파일을 찾을 수 없습니다."),
	FILE_DOWNLOAD_FAILED(INTERNAL_SERVER_ERROR, "파일 다운로드 실패하였습니다."),
	FILE_SAVE_FAILED(INTERNAL_SERVER_ERROR, "파일 저장 실패하였습니다."),
	FILE_SIZE_RETRIEVAL_FAILED(INTERNAL_SERVER_ERROR, "파일 크기 조회 실패하였습니다.");

	private final HttpStatus status;
	private final String message;

	@Override
	public String getCode() {
		return this.name();
	}
}
