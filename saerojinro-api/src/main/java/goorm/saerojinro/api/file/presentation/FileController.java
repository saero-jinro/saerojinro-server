package goorm.saerojinro.api.file.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "File", description = "파일 API")
public interface FileController {
	@Operation(
		summary = "파일 다운로드",
		description = "강의 자료를 다운로드합니다",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "파일 다운로드 성공",
				content = @Content(mediaType = "application/octet-stream")
			)
		}
	)
	ResponseEntity<Resource> downloadLectureFile(@RequestParam(value = "materialUri") String uri);
}
