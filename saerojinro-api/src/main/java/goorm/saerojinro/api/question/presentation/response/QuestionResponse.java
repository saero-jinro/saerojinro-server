package goorm.saerojinro.api.question.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.domain.question.domain.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record QuestionResponse(
    @Schema(description = "질문 ID", example = "1L", requiredMode = REQUIRED)
    Long id,

    @Schema(description = "질문 내용", example = "이 분야에 대한 공부는 어떤 방식으로 하는 것이 좋을까요?", requiredMode = REQUIRED)
    String content
){
    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .build();
    }
}
