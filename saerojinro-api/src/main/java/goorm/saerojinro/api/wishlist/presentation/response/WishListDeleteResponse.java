package goorm.saerojinro.api.wishlist.presentation.response;

import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.Builder;

@Builder
public record WishListDeleteResponse(
        Long id
){
    public static WishListDeleteResponse from(WishList wishList){
        return WishListDeleteResponse.builder()
                .id(wishList.getId())
                .build();
    }
}
