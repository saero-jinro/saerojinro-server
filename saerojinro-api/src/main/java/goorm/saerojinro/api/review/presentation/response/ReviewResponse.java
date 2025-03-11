package goorm.saerojinro.api.review.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import java.time.LocalDateTime;

import goorm.saerojinro.domain.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReviewResponse(
	@Schema(description = "리뷰 id", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "작성자", example = "도움이 많이 되었습니다.", requiredMode = REQUIRED)
	String reviewer,

	@Schema(description = "리뷰 내용", example = "도움이 많이 되었습니다.", requiredMode = REQUIRED)
	String content,

	@Schema(description = "리뷰 평점", example = "5.0", requiredMode = REQUIRED)
	Double rating,

	@Schema(description = "작성일", example = "2025-03-11T10:09:26.791828", requiredMode = REQUIRED)
	LocalDateTime createdAt
) {
	public static ReviewResponse from(Review review) {
		return ReviewResponse.builder()
			.id(review.getId())
			.reviewer(review.getUser().getName())
			.content(review.getContent())
			.rating(review.getRating())
			.createdAt(review.getCreatedAt())
			.build();
	}
}
