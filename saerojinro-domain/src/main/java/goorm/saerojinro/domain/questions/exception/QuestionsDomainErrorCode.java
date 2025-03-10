package goorm.saerojinro.domain.questions.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum QuestionsDomainErrorCode implements ExceptionCode {
    QUESTIONS_NOT_FOUND(NOT_FOUND, "해당하는 질문 데이터가 없습니다."),
    FORBIDDEN_QUESTIONS(FORBIDDEN, "해당하는 질문 데이터에 대한 권한이 없습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
