package goorm.saerojinro.api.timetable.presentation;

import goorm.saerojinro.api.timetable.presentation.response.TimetableResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Timetable", description = "시간표 API")
public interface TimetableController {

	@Operation(
		summary = "시간표 조회",
		description = "유저 ID로 시간표 목록을 조회 합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				content = @Content(schema = @Schema(implementation = TimetableResponse.class))
			)
		}
	)
	ResponseEntity<TimetableResponse> getTimetable(@PathVariable("id") Long attendeeId);
}
