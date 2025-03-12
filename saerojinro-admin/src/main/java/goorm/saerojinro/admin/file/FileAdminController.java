package goorm.saerojinro.admin.file;

import goorm.saerojinro.admin.file.request.FileSaveRequest;
import goorm.saerojinro.admin.file.response.FileSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "File", description = "운영자 파일 API")
public interface FileAdminController {
	@Operation(
		summary = "파일 저장",
		description = "운영자가 파일 저장",
		responses = {
			@ApiResponse(
				responseCode = "201",
				content = @Content(schema = @Schema(implementation = FileSaveResponse.class))
			)
		}
	)
	ResponseEntity<FileSaveResponse> create(@RequestBody FileSaveRequest request
	);
}
