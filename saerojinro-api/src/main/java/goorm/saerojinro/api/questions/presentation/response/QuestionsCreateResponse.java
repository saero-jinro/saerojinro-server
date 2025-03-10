package goorm.saerojinro.api.questions.presentation.response;

import lombok.Builder;

@Builder
public record QuestionsCreateResponse(
        Long id
) {
    public static QuestionsCreateResponse from(Long questionsId){
        return QuestionsCreateResponse.builder()
                .id(questionsId)
                .build();
    }
}
