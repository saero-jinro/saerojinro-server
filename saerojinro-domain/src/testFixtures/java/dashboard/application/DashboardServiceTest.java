package dashboard.application;

import goorm.saerojinro.domain.dashboard.application.DashboardService;
import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.dashboard.domain.DashboardRepository;
import goorm.saerojinro.domain.dashboard.dto.DashboardAggregation;
import mock.repository.FakeDashboardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardServiceTest {
	private DashboardService dashboardService;
	private DashboardRepository dashboardRepository;

	private final String TITLE = "title";
	private final String SPEAKER = "speaker";
	private final int RESERVATION = 1;
	private final int WISHLIST = 2;
	private final LocalDateTime START_TIME = LocalDateTime.now();

	@BeforeEach
	public void init() {
		dashboardRepository = new FakeDashboardRepository();
		dashboardService = new DashboardService(dashboardRepository);
	}

	@Test
	@DisplayName("findAll은 dashboard를 모두 조회한다")
	public void findAll_Success() {
		// given
		dashboardRepository.save(
			Dashboard.of(1L, TITLE, SPEAKER, RESERVATION, WISHLIST, RESERVATION + WISHLIST, START_TIME)
		);

		dashboardRepository.save(
			Dashboard.of(2L, TITLE, SPEAKER, 0, 0, 0, START_TIME)
		);

		// when
		DashboardAggregation all = dashboardService.findAll();

		//then
		assertEquals(1L, all.top10Dashboards().get(0).getId());
		assertEquals(2L, all.top10Dashboards().get(1).getId());
		assertEquals(2L, all.bottom10Dashboards().get(0).getId());
		assertEquals(1L, all.bottom10Dashboards().get(1).getId());
		assertEquals(START_TIME, all.top10Times().get(0));
	}

	@Test
	@DisplayName("save는 dashboard를 저장한다")
	public void save_Success() {
		// given
		Dashboard dashboard = Dashboard.of(3L, TITLE, SPEAKER, RESERVATION, WISHLIST,
			RESERVATION + WISHLIST, START_TIME);

		// when
		dashboardService.save(dashboard);

		//then
		List<Dashboard> all = dashboardRepository.findAll();
		assertEquals(1, all.size());
		assertEquals(dashboard.getId(), all.get(0).getId());
		assertEquals(dashboard.getTitle(), all.get(0).getTitle());
	}
}
