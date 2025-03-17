package goorm.saerojinro.domain.file.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.file.exception.FileDomainExceptionCode.*;

public class FileNotFoundException extends CustomException {
	public FileNotFoundException() {super(FILE_NOT_FOUND);}
}
