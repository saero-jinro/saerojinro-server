package goorm.saerojinro.speaker.api.presentation;

import goorm.saerojinro.speaker.api.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.speaker.api.presentation.request.LectureCreateRequest;
import goorm.saerojinro.speaker.api.presentation.response.LectureCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Speaker", description = "강연자 API")
public interface SpeakerController {
	@Operation(
		summary = "강의 생성",
		description = "강연자가 강의를 생성합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				content = @Content(schema = @Schema(implementation = LectureCreateResponse.class))
			)
		}
	)
	ResponseEntity<LectureCreateResponse> create(@PathVariable(name = "id") Long speakerId,
												 @RequestBody LectureCreateRequest request
	);

	@Operation(
		summary = "강의 수정",
		description = "강연자 혹은 관리자가 강의를 수정합니다",
		responses = {
			@ApiResponse(
				responseCode = "200"
			)
		}
	)
	ResponseEntity<Void> update(@PathVariable(name = "id") Long speakerId,
								@PathVariable Long lectureId,
								@RequestBody LectureUpdateRequest request
	);

	@Operation(
		summary = "강의 삭제",
		description = "강의를 삭제합니다",
		responses = {
			@ApiResponse(
				responseCode = "200"
			)
		}
	)
	ResponseEntity<Void> delete(@PathVariable(name = "id") Long speakerId,
								@PathVariable Long lectureId
	);
}
