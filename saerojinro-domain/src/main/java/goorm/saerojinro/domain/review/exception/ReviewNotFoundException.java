package goorm.saerojinro.domain.review.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.review.exception.ReviewDomainExceptionCode.NOT_FOUND_REVIEW;

public class ReviewNotFoundException extends CustomException {
    public ReviewNotFoundException() {
        super(NOT_FOUND_REVIEW);
    }
}
