package goorm.saerojinro.api.speaker.presentation;

import goorm.saerojinro.api.speaker.presentation.response.SpeakerDetailResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Speaker", description = "강연자 API")
public interface SpeakerController {
	@Operation(
		summary = "강연자 세부조회",
		description = "강연자의 세부 정보를 조회합니다",
		responses = {
			@ApiResponse(
				responseCode = "200",
				content = @Content(schema = @Schema(implementation = SpeakerDetailResponse.class))
			)
		}
	)
	ResponseEntity<SpeakerDetailResponse> findById(@RequestParam Long speakerId);
}
