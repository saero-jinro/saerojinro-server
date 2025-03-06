package goorm.saerojinro.domain.reissue.exception;

import static goorm.saerojinro.domain.reissue.exception.ReissueExceptionCode.REFRESH_TOKEN_NOT_FOUND;

import goorm.saerojinro.common.exception.CustomException;

public class RefreshTokenNotFoundException extends CustomException {
	public RefreshTokenNotFoundException() {
		super(REFRESH_TOKEN_NOT_FOUND);
	}
}
