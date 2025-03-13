package goorm.saerojinro.api.question.presentation.response;

import goorm.saerojinro.domain.question.domain.Question;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public record QuestionListResponse(
        @Schema()
        List<QuestionResponse> questionList,

        @Schema()
        Long totalCount
){
    public static QuestionListResponse from(List<Question> questionList) {
        List<QuestionResponse> responseList = questionList.stream()
                .map(QuestionResponse::from)
                .collect(Collectors.toList());
        return QuestionListResponse.builder()
                .questionList(responseList)
                .totalCount((long) responseList.size())
                .build();
    }
}
