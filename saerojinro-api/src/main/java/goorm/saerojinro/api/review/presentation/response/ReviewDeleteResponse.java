package goorm.saerojinro.api.review.presentation.response;

import lombok.Builder;

@Builder
public record ReviewDeleteResponse(
        Long id
)
{
    public static ReviewDeleteResponse from(Long reviewId){
        return ReviewDeleteResponse.builder()
                .id(reviewId)
                .build();
    }
}
