package goorm.saerojinro.domain.lecture.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.lecture.exception.LectureDomainExceptionCode.*;

public class LectureNotFoundException extends CustomException {
  public LectureNotFoundException() {
    super(LECTURE_NOT_FOUND);
  }
}
