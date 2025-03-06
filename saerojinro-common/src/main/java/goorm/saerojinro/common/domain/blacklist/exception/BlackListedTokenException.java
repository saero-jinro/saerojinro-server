package goorm.saerojinro.common.domain.blacklist.exception;

import static goorm.saerojinro.common.domain.blacklist.exception.BlackListExceptionCode.BLACK_LISTED_TOKEN;
import static goorm.saerojinro.common.domain.reissue.exception.ReissueExceptionCode.REFRESH_TOKEN_NOT_FOUND;

import goorm.saerojinro.common.exception.CustomException;

public class BlackListedTokenException extends CustomException {
	public BlackListedTokenException() {
		super(BLACK_LISTED_TOKEN);
	}
}
