package goorm.saerojinro.api.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record QuestionCreateResponse(
    @Schema(description = "질문 ID", example = "1L", requiredMode = REQUIRED)
    Long id
) {
    public static QuestionCreateResponse from(Long questionsId){
        return QuestionCreateResponse.builder()
                .id(questionsId)
                .build();
    }
}
