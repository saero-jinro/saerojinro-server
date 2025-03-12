package dashboard.application;

import goorm.saerojinro.admin.api.dashboard.application.DashboardScheduler;
import goorm.saerojinro.domain.dashboard.application.DashboardService;
import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.dashboard.domain.DashboardRepository;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import goorm.saerojinro.domain.wishlist.domain.WishListRepository;
import mock.repository.FakeDashboardRepository;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeReservationRepository;
import mock.repository.FakeWishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardSchedulerTest {
	private DashboardScheduler dashboardScheduler;
	private DashboardRepository dashboardRepository;

	private final String TITLE = "title";
	private final String SPEAKER = "speaker";
	private final int RESERVATION = 1;
	private final int WISHLIST = 2;
	private final LocalDateTime START_TIME = LocalDateTime.now();

	@BeforeEach
	public void init() {
		dashboardRepository = new FakeDashboardRepository();
		LectureRepository lectureRepository = new FakeLectureRepository();
		ReservationRepository reservationRepository = new FakeReservationRepository();
		WishListRepository wishListRepository = new FakeWishListRepository();

		DashboardService dashboardService = new DashboardService(dashboardRepository);
		LectureQueryService lectureQueryService = new LectureQueryService(lectureRepository);
		ReservationQueryService reservationQueryService = new ReservationQueryService(reservationRepository);
		WishListQueryService wishListQueryService = new WishListQueryService(wishListRepository);

		dashboardScheduler = new DashboardScheduler(dashboardService, lectureQueryService,
			reservationQueryService, wishListQueryService);

		Speaker speaker = Speaker.builder().name(SPEAKER).build();
		lectureRepository.save(
			Lecture.builder()
				.title(TITLE)
				.speaker(speaker)
				.startTime(START_TIME)
				.build()
		);

		lectureRepository.save(
			Lecture.builder()
				.title(TITLE + "1")
				.speaker(speaker)
				.startTime(START_TIME)
				.build()
		);
	}

	@Test
	@DisplayName("refreshDashboard는 기존의 dashboard가 있으면 ")
	public void refreshDashboard_Success() {
		// given
		dashboardRepository.save(
			Dashboard.of(1L, TITLE, SPEAKER, RESERVATION, WISHLIST, RESERVATION + WISHLIST, START_TIME)
		);
		dashboardRepository.save(
			Dashboard.of(2L, TITLE, SPEAKER, 0, 0, 0, START_TIME)
		);

		// when -> 현재 reservation, wishlist가 없으니 모두 0으로 초기화되어야함
		dashboardScheduler.refreshDashboard();

		// then
		List<Dashboard> all = dashboardRepository.findAll();
		assertEquals(2, all.size());
		assertEquals(0, all.get(0).getSum());
		assertEquals(0, all.get(0).getReservation());
		assertEquals(0, all.get(0).getWishlist());
		assertEquals(0, all.get(1).getSum());
		assertEquals(0, all.get(1).getReservation());
		assertEquals(0, all.get(1).getWishlist());
	}

	@Test
	@DisplayName("refreshDashboard")
	public void refreshDashboard_delivery_to_initDashboard() {
		// given
		// when
		dashboardScheduler.refreshDashboard();

		// then
		List<Dashboard> all = dashboardRepository.findAll();
		assertEquals(2, all.size());
		assertEquals(0, all.get(0).getSum());
		assertEquals(TITLE, all.get(0).getTitle());
		assertEquals(TITLE + "1", all.get(1).getTitle());
	}

	@Test
	@DisplayName("initDashboard")
	public void initDashboard_Success() {
		// given
		// when
		dashboardScheduler.initDashboard();

		// then
		List<Dashboard> all = dashboardRepository.findAll();
		assertEquals(2, all.size());
		assertEquals(0, all.get(0).getSum());
		assertEquals(TITLE, all.get(0).getTitle());
		assertEquals(TITLE + "1", all.get(1).getTitle());
	}
}
