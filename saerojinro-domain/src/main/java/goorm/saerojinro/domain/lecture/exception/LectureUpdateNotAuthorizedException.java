package goorm.saerojinro.domain.lecture.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.lecture.exception.LectureDomainExceptionCode.LECTURE_UPDATE_FORBIDDEN;

public class LectureUpdateNotAuthorizedException extends CustomException {
    public LectureUpdateNotAuthorizedException() {
        super(LECTURE_UPDATE_FORBIDDEN);
    }
}
