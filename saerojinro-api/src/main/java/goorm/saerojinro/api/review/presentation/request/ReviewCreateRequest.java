package goorm.saerojinro.api.review.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record ReviewCreateRequest(
        @Schema(description = "리뷰 내용", example = "도움이 많이 되었습니다.", requiredMode = REQUIRED)
        @NotBlank
        String content,

        @DecimalMin(value = "0.0", inclusive = true)
        @DecimalMax(value = "5.0", inclusive = true)
        @Schema(
                description = "리뷰 별점",
                example = "5.0",
                requiredMode = REQUIRED,
                minimum = "0.0",
                maximum = "5.0"
        )
        @NotNull
        Double rating
)
{
}
