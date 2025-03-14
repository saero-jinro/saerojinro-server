package goorm.saerojinro.admin.api.file.application;

import goorm.saerojinro.admin.api.file.presentation.request.FileSaveRequest;
import goorm.saerojinro.admin.api.file.presentation.response.FileReadResponse;
import goorm.saerojinro.admin.api.file.presentation.response.FileSaveResponse;
import goorm.saerojinro.domain.file.application.FileCommandService;
import goorm.saerojinro.domain.file.application.FileQueryService;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.file.application.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileAdminFacade {
	private final FileCommandService fileCommandService;
	private final FileQueryService fileQueryService;
	private final FileStorageService fileStorageService;

	private static final String DEFAULT_TEMP_DIR = "uploads/temp/";

	private static final String TEMP_MATERIALS = "materials/";
	private static final String TEMP_THUMBNAIL = "thumbnail/";
	private static final String TEMP_SPEAKER = "speaker/";

	@Transactional
	public FileSaveResponse saveLectureMaterials(MultipartFile file) {
		String logicalName = file.getOriginalFilename();
		String storedPath = fileStorageService.storeFile(file, DEFAULT_TEMP_DIR + TEMP_MATERIALS);
		return saveFile(logicalName, storedPath);
	}

	@Transactional
	public FileSaveResponse saveLecturePhoto(FileSaveRequest request) {
		String logicalName = extractFileName(request.uri());
		String storedPath = fileStorageService.storeFileFromUri(request.uri(), DEFAULT_TEMP_DIR + TEMP_THUMBNAIL);
		return saveFile(logicalName, storedPath);
	}

	@Transactional
	public FileSaveResponse saveSpeakerPhoto(FileSaveRequest request) {
		String logicalName = extractFileName(request.uri());
		String storedPath = fileStorageService.storeFileFromUri(request.uri(), DEFAULT_TEMP_DIR + TEMP_SPEAKER);
		return saveFile(logicalName, storedPath);
	}

	@Transactional(readOnly = true)
	public FileReadResponse findById(Long id) {
		File file = fileQueryService.getFileById(id);
		return FileReadResponse.from(file);
	}

	private FileSaveResponse saveFile(String logicalName, String storedPath) {
		Long fileSize = fileStorageService.getFileSize(storedPath);
		String extension = fileStorageService.getFileExtension(storedPath);
		File file = fileCommandService.save(logicalName, storedPath, fileSize, extension);
		return FileSaveResponse.from(file);
	}

	private String extractFileName(String uri) {
		int idx = uri.lastIndexOf('/');
		return (idx != -1) ? uri.substring(idx + 1) : uri;
	}
}
