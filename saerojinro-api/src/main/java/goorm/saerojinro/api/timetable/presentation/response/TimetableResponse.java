package goorm.saerojinro.api.timetable.presentation.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TimetableResponse(
	List<ReservationListResponse> reservation,
	List<WishlistListResponse> wishlist
) {
	public static TimetableResponse of(List<ReservationListResponse> reservation, List<WishlistListResponse> wishlist) {
		return TimetableResponse.builder()
			.reservation(reservation)
			.wishlist(wishlist)
			.build();
	}
}
