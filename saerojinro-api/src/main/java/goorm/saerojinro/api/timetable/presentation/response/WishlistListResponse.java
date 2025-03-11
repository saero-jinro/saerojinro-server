package goorm.saerojinro.api.timetable.presentation.response;

import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record WishlistListResponse(
	Long wishlistId,
	Long userId,
	Long lectureId,
	LocalDateTime startTime,
	LocalDateTime endTime,
	int currentReservation,
	int capacity
) {
	public static WishlistListResponse from(WishList wishList, int currentReservation) {
		return WishlistListResponse.builder()
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
