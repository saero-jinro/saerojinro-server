package goorm.saerojinro.api.question.presentation;

import goorm.saerojinro.api.question.presentation.request.QuestionCreateRequest;
import goorm.saerojinro.api.question.presentation.request.QuestionUpdateRequest;
import goorm.saerojinro.api.question.presentation.response.QuestionCreateResponse;
import goorm.saerojinro.api.question.presentation.response.QuestionListResponse;
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

@Tag(name = "Question", description = "질문 API")
public interface QuestionController {

    @Operation(
            summary = "질문 리스트 조회 API",
            description = "모든 질문 데이터를 조회 합니다."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = QuestionListResponse.class)))
    ResponseEntity<QuestionListResponse> getAllQuestions();

    @Operation(
            summary = "해당 강의 질문 조회 API",
            description = "해당 강의의 질문 리스트를 조회 합니다."
    )
    @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = QuestionListResponse.class)))
    ResponseEntity<QuestionListResponse> getByLecture(@PathVariable("lectureId") Long lectureId);

    @Operation(
            summary = "해당 강의 질문 생성 API",
            description = "해당 강의의 질문을 생성 합니다."
    )
    @ApiResponse(
            responseCode = "201",
            content = @Content(schema = @Schema(implementation = QuestionCreateResponse.class)))
    ResponseEntity<QuestionCreateResponse> create(
            @PathVariable("lectureId") Long lectureId,
            @Parameter(
                    description = "질문 생성 request 객체 입니다.",
                    required = true
            ) @Valid @RequestBody QuestionCreateRequest request);

    @Operation(
            summary = "질문 수정 API",
            description = "특정 질문 데이터를 수정 합니다."
    )
    @ApiResponse(responseCode = "204")
    ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @Parameter(
                    description = "질문 수정 request 객체 입니다.",
                    required = true
            ) @Valid @RequestBody QuestionUpdateRequest request);

    @Operation(
            summary = "질문 삭제 API",
            description = "특정 질문 데이터를 삭제 합니다."
    )
    @ApiResponse(responseCode = "204")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}
