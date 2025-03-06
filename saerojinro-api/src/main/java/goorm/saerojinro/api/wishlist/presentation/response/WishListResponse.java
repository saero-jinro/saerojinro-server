package goorm.saerojinro.api.wishlist.presentation.response;

import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.Builder;

import java.util.List;

@Builder
public record WishListResponse(
        List<WishList> wishLists
) {
    public static WishListResponse from(List<WishList> wishLists){
        return WishListResponse.builder()
                .wishLists(wishLists)
                .build();
    }
}
