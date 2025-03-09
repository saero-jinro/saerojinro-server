package goorm.saerojinro.domain.review.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.review.exception.ReviewDomainExceptionCode.UNAUTHORIZED_REVIEW;

public class ReviewNotAuthorizedException extends CustomException {
    public ReviewNotAuthorizedException() {
        super(UNAUTHORIZED_REVIEW);
    }
}
