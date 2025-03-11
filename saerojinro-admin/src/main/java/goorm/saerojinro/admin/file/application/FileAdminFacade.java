	package goorm.saerojinro.admin.file.application;

	import goorm.saerojinro.admin.file.request.FileSaveRequest;
	import goorm.saerojinro.admin.file.response.FileReadResponse;
	import goorm.saerojinro.admin.file.response.FileSaveResponse;
	import goorm.saerojinro.domain.file.application.FileCommandService;
	import goorm.saerojinro.domain.file.application.FileQueryService;
	import goorm.saerojinro.domain.file.domain.File;
	import goorm.saerojinro.domain.file.application.FileStorageService;
	import lombok.RequiredArgsConstructor;
	import org.springframework.stereotype.Component;
	import org.springframework.transaction.annotation.Transactional;

	@Component
	@RequiredArgsConstructor
	public class FileAdminFacade {
		private final FileCommandService fileCommandService;
		private final FileQueryService fileQueryService;
		private final FileStorageService fileStorageService;

		@Transactional
		public FileSaveResponse saveFile(FileSaveRequest request) {
			String logicalName = extractFileName(request.uri());
			String storedPath = fileStorageService.storeFileFromUri(request.uri());
			Long fileSize = fileStorageService.getFileSize(storedPath);
			String extension = fileStorageService.getFileExtension(storedPath);
			File file = fileCommandService.save(logicalName, storedPath, fileSize, extension);

			return FileSaveResponse.from(file);
		}

		private String extractFileName(String uri) {
			int idx = uri.lastIndexOf('/');
			return (idx != -1) ? uri.substring(idx + 1) : uri;
		}

		@Transactional(readOnly = true)
		public FileReadResponse findById(Long id) {
			File file = fileQueryService.getFileById(id);

			return FileReadResponse.from(file);
		}
	}
