package goorm.saerojinro.api.wishlist.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.domain.wishlist.domain.WishList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WishListDetailResponse(
    @Schema(description = "즐겨찾기 ID", example = "1", requiredMode = REQUIRED)
    Long id,

    @Schema(description = "강의 ID", example = "1001", requiredMode = REQUIRED)
    Long lectureId,

    @Schema(description = "강의자 이름", example = "박민준", requiredMode = REQUIRED)
    String speaker,

    @Schema(description = "강의 제목", example = "인생이란", requiredMode = REQUIRED)
    String lectureTitle,

    @Schema(description = "강의 시작 시간", example = "2025-03-01T10:00:00", requiredMode = REQUIRED)
    LocalDateTime startTime,

    @Schema(description = "강의 종료 시간", example = "2025-03-01T12:00:00", requiredMode = REQUIRED)
    LocalDateTime endTime
) {
    public static WishListDetailResponse from(WishList wishList){
        return WishListDetailResponse.builder()
                .id(wishList.getId())
                .lectureId(wishList.getLecture().getId())
                .speaker(wishList.getLecture().getSpeaker().getName())
                .lectureTitle(wishList.getLecture().getTitle())
                .startTime(wishList.getLecture().getStartTime())
                .endTime(wishList.getLecture().getEndTime())
                .build();
    }
}
