package goorm.saerojinro.api.wishlist.presentation.response;

import java.util.List;

import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.Builder;

@Builder
public record WishListResponse(
	List<WishListDetailResponse> response
) {
	public static WishListResponse from(List<WishList> wishLists) {
		return WishListResponse.builder()
			.response(wishLists.stream()
				.map(WishListDetailResponse::from)
				.toList()
			)
			.build();
	}
}
