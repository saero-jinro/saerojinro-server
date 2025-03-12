package dashboard;

import goorm.saerojinro.admin.api.dashboard.application.DashboardFacade;
import goorm.saerojinro.admin.api.dashboard.presentation.response.DashboardResponse;
import goorm.saerojinro.domain.dashboard.application.DashboardService;
import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.dashboard.domain.DashboardRepository;
import mock.repository.FakeDashboardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardFacadeTest {
	private DashboardFacade dashboardFacade;

	private final String TITLE = "title";
	private final String SPEAKER = "speaker";
	private final int RESERVATION = 1;
	private final int WISHLIST = 2;
	private final LocalDateTime START_TIME = LocalDateTime.now();


	@BeforeEach
	public void init() {
		DashboardRepository dashboardRepository = new FakeDashboardRepository();
		DashboardService dashboardService = new DashboardService(dashboardRepository);
		dashboardFacade = new DashboardFacade(dashboardService);

		dashboardRepository.save(
			Dashboard.of(1L, TITLE, SPEAKER, RESERVATION, WISHLIST, RESERVATION + WISHLIST, START_TIME)
		);

		dashboardRepository.save(
			Dashboard.of(2L, TITLE, SPEAKER, 0, 0, 0, START_TIME)
		);
	}

	@Test
	@DisplayName("getDashboard는 저장된 모든 dashboard를 반환합니다")
	public void getDashboard_Success() {
		// given

		// when
		DashboardResponse dashboard = dashboardFacade.getDashboard();

		// then
		// 저장 테스트
		assertEquals(1L, dashboard.lectureHighRank().get(0).lectureId());
		assertEquals(1, dashboard.lectureHighRank().get(0).rank());
		assertEquals(TITLE, dashboard.lectureHighRank().get(0).title());
		assertEquals(SPEAKER, dashboard.lectureHighRank().get(0).speaker());
		assertEquals(RESERVATION, dashboard.lectureHighRank().get(0).reservation());
		assertEquals(WISHLIST, dashboard.lectureHighRank().get(0).wishlist());

		// 순서 테스트
		assertEquals(2L, dashboard.lectureHighRank().get(1).lectureId());

		// low 순서 테스트
		assertEquals(2L, dashboard.lectureLowRank().get(0).lectureId());
		assertEquals(1L, dashboard.lectureLowRank().get(1).lectureId());

		// time 테스트
		assertEquals(START_TIME, dashboard.timeRank().get(0).startTime());
		assertEquals(1, dashboard.timeRank().get(0).rank());
	}
}
