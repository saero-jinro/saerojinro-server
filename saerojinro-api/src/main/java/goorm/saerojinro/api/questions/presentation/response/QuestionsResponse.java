package goorm.saerojinro.api.questions.presentation.response;

import goorm.saerojinro.domain.questions.domain.Questions;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QuestionsResponse {
    private Long id;
    private String content;

    public static QuestionsResponse from(Questions question) {
        return QuestionsResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .build();
    }
}
