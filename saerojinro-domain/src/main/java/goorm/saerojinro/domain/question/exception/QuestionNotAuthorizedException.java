package goorm.saerojinro.domain.question.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.question.exception.QuestionDomainErrorCode.FORBIDDEN_QUESTIONS;

public class QuestionNotAuthorizedException extends CustomException {
    public QuestionNotAuthorizedException() {
        super(FORBIDDEN_QUESTIONS);
    }
}
