package goorm.saerojinro.domain.file.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.file.exception.FileDomainExceptionCode.FILE_DOWNLOAD_FAILED;

public class FileDownloadFailedException extends CustomException {
	public FileDownloadFailedException() {super(FILE_DOWNLOAD_FAILED);}
}
