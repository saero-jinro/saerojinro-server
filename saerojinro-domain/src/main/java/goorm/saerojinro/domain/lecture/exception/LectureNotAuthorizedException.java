package goorm.saerojinro.domain.lecture.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.lecture.exception.LectureDomainExceptionCode.LECTURE_FORBIDDEN;


public class LectureNotAuthorizedException extends CustomException {
    public LectureNotAuthorizedException() {
        super(LECTURE_FORBIDDEN);
    }
}
