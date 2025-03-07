package goorm.saerojinro.api.review.presentation.response;

import goorm.saerojinro.domain.review.domain.Review;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReviewUpdateResponse(
        Long id,
        Long lectureId,
        Long userId,
        String content,
        Double rating,
        LocalDateTime updateAt
) {
    public static ReviewUpdateResponse from(Review review){
        return ReviewUpdateResponse.builder()
                .id(review.getId())
                .lectureId(review.getLecture().getId())
                .userId(review.getUser().getId())
                .content(review.getContent())
                .rating(review.getRating())
                .updateAt(review.getUpdatedAt())
                .build();
    }
}
