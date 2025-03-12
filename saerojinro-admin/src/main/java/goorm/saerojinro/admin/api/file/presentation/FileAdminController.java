package goorm.saerojinro.admin.api.file.presentation;

import goorm.saerojinro.admin.api.file.presentation.request.FileSaveRequest;
import goorm.saerojinro.admin.api.file.presentation.response.FileSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File", description = "운영자 파일 API")
public interface FileAdminController {
	@Operation(
		summary = "강의 썸네일 파일 저장",
		description = "강의 썸네일 저장",
		responses = {
			@ApiResponse(
				responseCode = "201",
				content = @Content(schema = @Schema(implementation = FileSaveResponse.class))
			)
		}
	)
	ResponseEntity<FileSaveResponse> saveLecturePhoto(@RequestBody FileSaveRequest request
	);

	@Operation(
		summary = "강연자 프로필 사진 파일 저장",
		description = "강연자 사진 저장",
		responses = {
			@ApiResponse(
				responseCode = "201",
				content = @Content(schema = @Schema(implementation = FileSaveResponse.class))
			)
		}
	)
	ResponseEntity<FileSaveResponse> saveSpeakerPhoto(@RequestBody FileSaveRequest request
	);

	@Operation(
		summary = "강의 자료 파일 저장",
		description = "강의 자료 저장",
		responses = {
			@ApiResponse(
				responseCode = "201",
				content = @Content(schema = @Schema(implementation = FileSaveResponse.class)
					, mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
			)
		}
	)
	ResponseEntity<FileSaveResponse> uploadLectureMaterials(@RequestPart(value = "file") MultipartFile file
	);
}
