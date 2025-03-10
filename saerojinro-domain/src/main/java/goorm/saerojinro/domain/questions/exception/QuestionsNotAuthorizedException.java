package goorm.saerojinro.domain.questions.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.questions.exception.QuestionsDomainErrorCode.FORBIDDEN_QUESTIONS;

public class QuestionsNotAuthorizedException extends CustomException {
    public QuestionsNotAuthorizedException() {
        super(FORBIDDEN_QUESTIONS);
    }
}
