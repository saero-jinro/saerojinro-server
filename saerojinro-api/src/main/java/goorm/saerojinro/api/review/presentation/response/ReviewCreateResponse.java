package goorm.saerojinro.api.review.presentation.response;

import goorm.saerojinro.domain.review.domain.Review;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewCreateResponse(
        Long id,
        Long lectureId,
        Long userId,
        String content,
        Double rating,
        LocalDateTime createdAt
)
{
    public static ReviewCreateResponse from(Review review){
        return ReviewCreateResponse.builder()
                .id(review.getId())
                .lectureId(review.getLecture().getId())
                .userId(review.getUser().getId())
                .content(review.getContent())
                .rating(review.getRating())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
