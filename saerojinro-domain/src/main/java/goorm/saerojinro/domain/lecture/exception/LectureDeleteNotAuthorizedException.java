package goorm.saerojinro.domain.lecture.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.lecture.exception.LectureDomainExceptionCode.LECTURE_DELETE_FORBIDDEN;

public class LectureDeleteNotAuthorizedException extends CustomException {
    public LectureDeleteNotAuthorizedException() {
        super(LECTURE_DELETE_FORBIDDEN);
    }
}
