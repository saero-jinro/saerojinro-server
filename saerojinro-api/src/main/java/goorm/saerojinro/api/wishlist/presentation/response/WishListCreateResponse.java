package goorm.saerojinro.api.wishlist.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.domain.wishlist.domain.WishList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record WishListCreateResponse(
    @Schema(description = "즐겨찾기 ID", example = "1", requiredMode = REQUIRED)
    Long id
) {
    public static WishListCreateResponse from(WishList wishList) {
        return WishListCreateResponse.builder()
                .id(wishList.getId())
                .build();
    }
}
