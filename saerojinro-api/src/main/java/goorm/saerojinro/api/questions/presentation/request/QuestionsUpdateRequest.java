package goorm.saerojinro.api.questions.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

@Builder
public record QuestionsUpdateRequest(
        @Schema(description = "질문 수정 내용", example = "수정된 질문 내용 입니다.", requiredMode = REQUIRED)
        @NotBlank
        String content
) {
}
