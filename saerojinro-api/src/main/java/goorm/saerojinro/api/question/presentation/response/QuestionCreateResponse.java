package goorm.saerojinro.api.question.presentation.response;

import lombok.Builder;

@Builder
public record QuestionCreateResponse(
        Long id
) {
    public static QuestionCreateResponse from(Long questionsId){
        return QuestionCreateResponse.builder()
                .id(questionsId)
                .build();
    }
}
