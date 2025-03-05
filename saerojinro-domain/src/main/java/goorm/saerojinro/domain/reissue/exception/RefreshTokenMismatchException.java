package goorm.saerojinro.domain.reissue.exception;

import static goorm.saerojinro.domain.reissue.exception.ReissueExceptionCode.REFRESH_TOKEN_MISMATCH;

import goorm.saerojinro.common.exception.CustomException;

public class RefreshTokenMismatchException extends CustomException {
	public RefreshTokenMismatchException() { super(REFRESH_TOKEN_MISMATCH);}
}
