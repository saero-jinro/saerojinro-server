package goorm.saerojinro.api.review.presentation;

import goorm.saerojinro.api.review.presentation.request.ReviewCreateRequest;
import goorm.saerojinro.api.review.presentation.request.ReviewUpdateRequest;
import goorm.saerojinro.api.review.presentation.response.ReviewCreateResponse;
import goorm.saerojinro.api.review.presentation.response.ReviewListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Review", description = "리뷰 API")
public interface ReviewController {

    @Operation(
            summary = "모든 리뷰 조회 API",
            description = "모든 리뷰 데이터를 조회 합니다."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ReviewListResponse.class)))
    ResponseEntity<ReviewListResponse> getAllReview();

    @Operation(
            summary = "강의 별 리뷰 조회 API",
            description = "강의 별로 저장된 모든 리뷰 데이터를 조회 합니다."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ReviewListResponse.class)))
    ResponseEntity<ReviewListResponse> getByLecture(@PathVariable("lectureId") Long lectureId);

    @Operation(
            summary = "강의 별 리뷰 생성 API",
            description = "강의 별로 새로운 리뷰 데이터를 생성 합니다."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = ReviewCreateResponse.class)))
    ResponseEntity<ReviewCreateResponse> create(
            @PathVariable("lectureId") Long lectureId,
            @Parameter(
            description = "리뷰 생성 request 객체 입니다.",
            required = true
            ) @Valid @RequestBody ReviewCreateRequest request);

    @Operation(
            summary = "강의 별 리뷰 수정 API",
            description = "강의 별로 특정 리뷰 데이터를 수정 합니다."
    )
    @ApiResponse(
            responseCode = "204",
            description = "리뷰 수정 성공"
    )
    ResponseEntity<Void> update(
            @PathVariable("reviewId") Long reviewId,
            @Parameter(
                    description = "리뷰 수정 request 객체 입니다.",
                    required = true
            ) @Valid @RequestBody ReviewUpdateRequest request);

    @Operation(
            summary = "강의 별 리뷰 삭제 API",
            description = "강의 별로 특정 리뷰 데이터를 삭제 합니다."
    )
    @ApiResponse(
            responseCode = "204",
            description = "리뷰 삭제 성공"
    )
    ResponseEntity<Void> delete(@PathVariable("reviewId") Long reviewId);
}
