package goorm.saerojinro.api.timetable.application;

import goorm.saerojinro.api.timetable.presentation.response.ReservationListResponse;
import goorm.saerojinro.api.timetable.presentation.response.TimetableResponse;
import goorm.saerojinro.api.timetable.presentation.response.WishlistListResponse;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimetableFacade {
	private final UserQueryService userQueryService;
	private final ReservationQueryService reservationQueryService;
	private final WishListQueryService wishListQueryService;

	public TimetableResponse getTimetable(Long attendeeId) {
		User user = userQueryService.getById(attendeeId);
		List<Reservation> reservationList = reservationQueryService.getAllReservationByUser(user);
		List<WishList> wishListList = wishListQueryService.getAllByUser(user);

		List<ReservationListResponse> reservationListResponseList = new ArrayList<>();
		List<WishlistListResponse> wishListResponseList = new ArrayList<>();

		for (Reservation reservation : reservationList) {
			List<Reservation> thisLectureReservationList = reservationQueryService
				.getAllByLectureId(reservation.getLecture().getId());

			reservationListResponseList.add(
				ReservationListResponse.from(reservation, (int) (thisLectureReservationList.size() * 0.8))
			);
		}

		for (WishList wishList : wishListList) {
			List<Reservation> thisLectureReservationList = reservationQueryService
				.getAllByLectureId(wishList.getLecture().getId());

			wishListResponseList.add(
				WishlistListResponse.from(wishList, (int) (thisLectureReservationList.size() * 0.8))
			);
		}

		return TimetableResponse.of(reservationListResponseList, wishListResponseList);
	}
}
