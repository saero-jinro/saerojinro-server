package goorm.saerojinro.api.lecture.presentation;

import java.time.LocalDate;
import java.time.LocalDateTime;

import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureSummaryListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Lecture", description = "강의 API")
public interface LectureController {
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
	ResponseEntity<LectureDetailResponse> getById(@PathVariable Long lectureId);

	@Operation(
		summary = "날짜별 강의 조회",
		description = "주어진 날짜(yyyy-MM-dd)에 시작하는 강의를 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				content = @Content(schema = @Schema(implementation = LectureListResponse.class))
			)
		}
	)
	ResponseEntity<LectureListResponse> getByDate(
		@Parameter(
			name = "date",
			description = "행사 일자 (ISO 8601 형식: yyyy-MM-dd)",
			example = "2025-03-15"
		)
		@RequestParam("date")
		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
	);

	@Operation(
		summary = "유저 추천 강의 조회",
		description = "해당 시간대의 추천 강의를 조회합니다.",
		responses = {
			@ApiResponse(
				responseCode = "200",
				content = @Content(schema = @Schema(implementation = LectureSummaryListResponse.class))
			)
		},
		parameters = {
			@Parameter(
				name = "startTime",
				description = "강의 시작 시간 (ISO 8601 형식: yyyy-MM-dd'T'HH:mm:ss)",
				example = "2025-03-15T10:00:00"
			)
		}
	)
	ResponseEntity<LectureSummaryListResponse> getRecommendationLectures(
		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime
	);
}
