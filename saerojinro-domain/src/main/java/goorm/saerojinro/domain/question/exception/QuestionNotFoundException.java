package goorm.saerojinro.domain.question.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.question.exception.QuestionDomainErrorCode.QUESTIONS_NOT_FOUND;

public class QuestionNotFoundException extends CustomException {
    public QuestionNotFoundException() {
        super(QUESTIONS_NOT_FOUND);
    }
}
