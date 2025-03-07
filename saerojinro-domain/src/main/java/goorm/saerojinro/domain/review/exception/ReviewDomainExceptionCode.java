package goorm.saerojinro.domain.review.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@AllArgsConstructor
public enum ReviewDomainExceptionCode implements ExceptionCode {
    UNAUTHORIZED_REVIEW(UNAUTHORIZED, "리뷰에 관한 권한이 부족합니다."),
    NOT_FOUND_REVIEW(NOT_FOUND, "해당하는 리뷰 데이터가 없습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}