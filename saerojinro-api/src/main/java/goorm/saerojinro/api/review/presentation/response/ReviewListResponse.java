package goorm.saerojinro.api.review.presentation.response;

import goorm.saerojinro.domain.review.domain.Review;
import lombok.Builder;

import java.util.List;

@Builder
public record ReviewListResponse(
        List<Review> reviews
) {
    public static ReviewListResponse from(List<Review> reviews){
        return ReviewListResponse.builder()
                .reviews(reviews)
                .build();
    }
}
