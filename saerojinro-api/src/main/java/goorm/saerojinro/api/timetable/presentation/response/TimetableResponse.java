package goorm.saerojinro.api.timetable.presentation.response;

import lombok.Builder;

import java.util.List;

@Builder
public record TimetableResponse(
	List<ReservationsResponse> reservation,
	List<WishlistsResponse> wishlist
) {
	public static TimetableResponse of(List<ReservationsResponse> reservation, List<WishlistsResponse> wishlist) {
		return TimetableResponse.builder()
			.reservation(reservation)
			.wishlist(wishlist)
			.build();
	}
}
