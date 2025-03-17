package goorm.saerojinro.admin.api.dashboard.application;

import goorm.saerojinro.domain.dashboard.application.DashboardService;
import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.dto.LectureReservationCountDto;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import goorm.saerojinro.domain.wishlist.dto.LectureWishlistCountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardScheduler {
	private final DashboardService dashboardService;
	private final LectureQueryService lectureQueryService;
	private final ReservationQueryService reservationQueryService;
	private final WishListQueryService wishListQueryService;

	@Transactional
	@Scheduled(cron = "0 0 * * * *")
	public void refreshDashboard() {
		// 전체 lecture 조회
		List<Lecture> lectureList = lectureQueryService.getAll();

		// reservation, wishlist 조회
		Map<Long, Integer> reservationCounts = new HashMap<>();
		for (LectureReservationCountDto l : reservationQueryService.getReservationAllLecture()) {
			reservationCounts.put(l.lectureId(), Math.toIntExact(l.reservationCount()));
		}

		Map<Long, Integer> wishlistCounts = new HashMap<>();
		for (LectureWishlistCountDto l : wishListQueryService.getWishlistAllLecture()) {
			wishlistCounts.put(l.lectureId(), Math.toIntExact(l.wishlistCount()));
		}

		// dashboard 저장
		for (Lecture lecture : lectureList) {
			Long id = lecture.getId();
			int reservation = reservationCounts.get(id) == null ? 0 : reservationCounts.get(id);
			int wishlist = wishlistCounts.get(id) == null ? 0 : wishlistCounts.get(id);

			dashboardService.save(
				Dashboard.of(id, lecture.getTitle(), lecture.getSpeaker().getName(),
					reservation, wishlist, reservation + wishlist, lecture.getStartTime())
			);
		}
	}
}
