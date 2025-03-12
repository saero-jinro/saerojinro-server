package goorm.saerojinro.api.question.presentation.response;

import goorm.saerojinro.domain.question.domain.Question;
import lombok.Builder;

@Builder
public record QuestionResponse(
        Long id,
        String content
){
    public static QuestionResponse from(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .build();
    }
}
