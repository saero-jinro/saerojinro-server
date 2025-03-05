package goorm.saerojinro.domain.reissue.exception;

import static goorm.saerojinro.domain.reissue.exception.ReissueExceptionCode.TOKEN_NOT_FOUND;

import goorm.saerojinro.common.exception.CustomException;

public class TokenNotFoundException extends CustomException {
	public TokenNotFoundException() {
		super(TOKEN_NOT_FOUND);
	}
}
