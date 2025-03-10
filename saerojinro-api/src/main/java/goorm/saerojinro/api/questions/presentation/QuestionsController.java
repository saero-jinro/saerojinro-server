package goorm.saerojinro.api.questions.presentation;

import goorm.saerojinro.api.questions.presentation.request.QuestionsCreateRequest;
import goorm.saerojinro.api.questions.presentation.request.QuestionsUpdateRequest;
import goorm.saerojinro.api.questions.presentation.response.QuestionsCreateResponse;
import goorm.saerojinro.api.questions.presentation.response.QuestionsListResponse;
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

@Tag(name = "Questions", description = "질문 API")
public interface QuestionsController {

    @Operation(
            summary = "모든 질문 조회 API",
            description = "모든 질문 데이터를 조회 합니다."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = QuestionsListResponse.class)))
    ResponseEntity<QuestionsListResponse> getAllQuestions();

    @Operation(
            summary = "강의 별 질문 조회 API",
            description = "강의 별로 저장된 모든 질문 데이터를 조회 합니다."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = QuestionsListResponse.class)))
    ResponseEntity<QuestionsListResponse> getByLecture(@PathVariable("lectureId") Long lectureId);

    @Operation(
            summary = "강의 별 질문 생성 API",
            description = "강의 별로 새로운 질문 데이터를 생성 합니다."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = QuestionsCreateResponse.class)))
    ResponseEntity<QuestionsCreateResponse> create(
            @PathVariable("lectureId") Long lectureId,
            @Parameter(
                    description = "질문 생성 request 객체 입니다.",
                    required = true
            ) @Valid @RequestBody QuestionsCreateRequest request);

    @Operation(
            summary = "강의 별 질문 수정 API",
            description = "강의 별로 특정 질문 데이터를 수정 합니다."
    )
    @ApiResponse(
            responseCode = "204",
            description = "질문 수정 성공"
    )
    ResponseEntity<Void> update(
            @PathVariable("questionsId") Long questionsId,
            @Parameter(
                    description = "질문 수정 request 객체 입니다.",
                    required = true
            ) @Valid @RequestBody QuestionsUpdateRequest request);

    @Operation(
            summary = "강의 별 질문 삭제 API",
            description = "강의 별로 특정 질문 데이터를 삭제 합니다."
    )
    @ApiResponse(
            responseCode = "204",
            description = "질문 삭제 성공"
    )
    ResponseEntity<Void> delete(@PathVariable("questionsId") Long questionsId);
}
