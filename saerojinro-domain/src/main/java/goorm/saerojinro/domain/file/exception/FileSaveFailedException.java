package goorm.saerojinro.domain.file.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.file.exception.FileDomainExceptionCode.FILE_SAVE_FAILED;

public class FileSaveFailedException extends CustomException {
	public FileSaveFailedException() {super(FILE_SAVE_FAILED);}
}
