package goorm.saerojinro.api.timetable.presentation.response;

import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WishlistsResponse(
	Long wishlistId,
	Long userId,
	Long lectureId,
	LocalDateTime startTime,
	LocalDateTime endTime,
	int currentReservation,
	int capacity
) {
	private static WishlistsResponse from(WishList wishList, int currentReservation) {
		return WishlistsResponse.builder()
			.wishlistId(wishList.getId())
			.userId(wishList.getUser().getId())
			.lectureId(wishList.getLecture().getId())
			.startTime(wishList.getLecture().getStartTime())
			.endTime(wishList.getLecture().getEndTime())
			.currentReservation(currentReservation)
			.capacity(Math.toIntExact(wishList.getLecture().getMaxCapacity()))
			.build();
	}
}
