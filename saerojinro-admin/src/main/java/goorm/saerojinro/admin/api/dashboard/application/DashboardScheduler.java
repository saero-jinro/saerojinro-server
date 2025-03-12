package goorm.saerojinro.admin.api.dashboard.application;

import goorm.saerojinro.domain.dashboard.application.DashboardService;
import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

	@Scheduled(cron = "0 0 * * * *")
	public void refreshDashboard() {
		List<Dashboard> all = dashboardService.findAll();
		if (all.isEmpty()) {
			initDashboard();
			return;
		}

		Map<Long, Integer> reservationCounts = new HashMap<>();
		for (Dashboard dashboard : all) {
			int count = reservationQueryService.countByLectureId(dashboard.getId());
			reservationCounts.put(dashboard.getId(), count);
		}

		// wishlist
		Map<Long, Integer> wishlistCounts = new HashMap<>();
		for (Dashboard dashboard : all) {
			int count = wishListQueryService.countByLectureId(dashboard.getId());
			wishlistCounts.put(dashboard.getId(), count);
		}

		// dashboard 생성
		for (Dashboard dashboard : all) {
			Long id = dashboard.getId();
			Integer reservation = reservationCounts.get(id);
			Integer wishlist = wishlistCounts.get(id);

			dashboardService.save(
				Dashboard.from(dashboard, reservation, wishlist, reservation + wishlist)
			);
		}
	}

	public void initDashboard() {
		// 전체 lecture 조회
		List<Lecture> lectureList = lectureQueryService.getAllLecture();

		// reserv, wishlist 조회
		Map<Long, Integer> reservationCounts = new HashMap<>();
		for (Lecture lecture : lectureList) {
			int count = reservationQueryService.countByLectureId(lecture.getId());
			reservationCounts.put(lecture.getId(), count);
		}

		Map<Long, Integer> wishlistCounts = new HashMap<>();
		for (Lecture lecture : lectureList) {
			int count = wishListQueryService.countByLectureId(lecture.getId());
			wishlistCounts.put(lecture.getId(), count);
		}

		// dashboard 저장
		for (Lecture lecture : lectureList) {
			Long id = lecture.getId();
			Integer reservation = reservationCounts.get(id);
			Integer wishlist = wishlistCounts.get(id);

			dashboardService.save(
				Dashboard.of(id, lecture.getTitle(), lecture.getSpeaker().getName(),
					reservation, wishlist, reservation + wishlist, lecture.getStartTime())
			);
		}
	}
}
