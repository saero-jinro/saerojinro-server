package goorm.saerojinro.api.questions.presentation.response;

import goorm.saerojinro.domain.questions.domain.Questions;
import lombok.Builder;

import java.util.List;

@Builder
public record QuestionsListResponse(
        List<Questions> questionsList
){
    public static QuestionsListResponse from(List<Questions> questionsList){
        return QuestionsListResponse.builder()
                .questionsList(questionsList)
                .build();
    }
}
