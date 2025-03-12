package goorm.saerojinro.api.questions.presentation.response;

import goorm.saerojinro.domain.questions.domain.Questions;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record QuestionsListResponse(
        List<QuestionsResponse> questionsList
) {
    public static QuestionsListResponse from(List<Questions> questionsList) {
        List<QuestionsResponse> responseList = questionsList.stream()
                .map(QuestionsResponse::from)
                .collect(Collectors.toList());
        return QuestionsListResponse.builder()
                .questionsList(responseList)
                .build();
    }
}
