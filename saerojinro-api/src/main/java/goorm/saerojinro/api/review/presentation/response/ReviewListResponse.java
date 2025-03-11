package goorm.saerojinro.api.review.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.domain.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record ReviewListResponse(
    @Schema(description = "리뷰 리스트",
        example = "[{"
            + "\"id\": \"1\", "
            + "\"content\": \"도움이 많이 되었습니다.\", "
            + "\"rating\": \"5\", "
            + "\"createdAt\": \"2025-03-11T10:09:26.791828\"}]",
        requiredMode = REQUIRED)
        List<ReviewResponse> reviews
) {
    public static ReviewListResponse from(List<Review> reviews){
        return ReviewListResponse.builder()
                .reviews(reviews.stream()
                    .map(ReviewResponse::from)
                    .toList())
                .build();
    }
}
