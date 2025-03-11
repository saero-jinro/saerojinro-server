package goorm.saerojinro.domain.questions.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.questions.exception.QuestionsDomainErrorCode.QUESTIONS_NOT_FOUND;

public class QuestionsNotFoundException extends CustomException {
    public QuestionsNotFoundException() {
        super(QUESTIONS_NOT_FOUND);
    }
}
