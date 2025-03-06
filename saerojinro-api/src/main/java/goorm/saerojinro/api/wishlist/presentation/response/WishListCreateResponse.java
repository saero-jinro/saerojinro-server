package goorm.saerojinro.api.wishlist.presentation.response;

import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.Builder;

@Builder
public record WishListCreateResponse(
        Long id
) {
    public static WishListCreateResponse from(WishList wishList) {
        return WishListCreateResponse.builder()
                .id(wishList.getId())
                .build();
    }
}
