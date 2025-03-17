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
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TimetableFacade {
	private final UserQueryService userQueryService;
	private final ReservationQueryService reservationQueryService;
	private final WishListQueryService wishListQueryService;

	@Transactional(readOnly = true)
	public TimetableResponse getTimetable() {
		User user = userQueryService.me();
		if (user == null)
			return TimetableResponse.of(new ArrayList<>(), new ArrayList<>());

		List<Reservation> reservationList = reservationQueryService.getAllReservationByUser(user.getId());
		List<WishList> wishListList = wishListQueryService.getAllByUser(user);

		List<ReservationListResponse> reservationListResponseList = new ArrayList<>();
		List<WishlistListResponse> wishListResponseList = new ArrayList<>();

		for (Reservation reservation : reservationList) {
			int size = reservationQueryService.countByLectureId(reservation.getLecture().getId());
			reservationListResponseList.add(
				ReservationListResponse.from(reservation, size)
			);
		}

		for (WishList wishList : wishListList) {
			int size = reservationQueryService.countByLectureId(wishList.getLecture().getId());
			wishListResponseList.add(
				WishlistListResponse.from(wishList, size)
			);
		}

		return TimetableResponse.of(reservationListResponseList, wishListResponseList);
	}
}
