package goorm.saerojinro.admin.api.lecture.presentation;

import goorm.saerojinro.admin.api.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.response.LectureCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Lecture", description = "운영자 강의 API")
public interface LectureAdminController {
	@Operation(
		summary = "강의 생성",
		description = "운영자가 강의를 생성합니다.",
		responses = {
			@ApiResponse(
				responseCode = "201",
				content = @Content(schema = @Schema(implementation = LectureCreateResponse.class))
			)
		}
	)
	ResponseEntity<LectureCreateResponse> create(@RequestBody LectureCreateRequest request
	);

	@Operation(
		summary = "강의 수정",
		description = "운영자가 강의를 수정합니다",
		responses = {
			@ApiResponse(
				responseCode = "200"
			)
		}
	)
	ResponseEntity<Void> update(@PathVariable Long lectureId,
								@RequestBody LectureUpdateRequest request
	);

	@Operation(
		summary = "강의 삭제",
		description = "운영자가 강의를 삭제합니다",
		responses = {
			@ApiResponse(
				responseCode = "204"
			)
		}
	)
	ResponseEntity<Void> delete(@PathVariable Long lectureId
	);
}
