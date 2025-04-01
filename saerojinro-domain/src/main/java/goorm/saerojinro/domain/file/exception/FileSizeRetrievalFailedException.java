package goorm.saerojinro.domain.file.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.file.exception.FileDomainExceptionCode.FILE_SIZE_RETRIEVAL_FAILED;

public class FileSizeRetrievalFailedException extends CustomException {
	public FileSizeRetrievalFailedException() {super(FILE_SIZE_RETRIEVAL_FAILED);}
}
