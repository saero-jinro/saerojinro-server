package goorm.saerojinro.api.question.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

@Builder
public record QuestionCreateRequest(
        @Schema(description = "질문 내용", example = "강의 구성이 궁금합니다.", requiredMode = REQUIRED)
        @NotBlank
        String content
) {
}
