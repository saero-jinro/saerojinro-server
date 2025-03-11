package goorm.saerojinro.api.lecture.presentation;

import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponseByAll;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponseByDate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Lecture", description = "강의 API")
public interface LectureController {
	@Operation(
		summary = "전체 강의 조회",
		description = "저장된 모든 강의를 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				content = @Content(schema = @Schema(implementation = LectureListResponseByAll.class))
			)
		}
	)
	ResponseEntity<LectureListResponseByAll> getAllLecture();

	@Operation(
		summary = "강의 상세 조회",
		description = "강의 ID를 통해 강의의 상세 정보를 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				content = @Content(schema = @Schema(implementation = LectureDetailResponse.class))
			)
		}
	)
	ResponseEntity<LectureDetailResponse> getByLectureId(@PathVariable Long lectureId);

	@Operation(
		summary = "날짜별 강의 조회",
		description = "주어진 날짜(yyyy-MM-dd)에 시작하는 강의를 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				content = @Content(schema = @Schema(implementation = LectureListResponseByAll.class))
			)
		}
	)
	ResponseEntity<LectureListResponseByDate> getByDate(@RequestParam("day") String day);
}
