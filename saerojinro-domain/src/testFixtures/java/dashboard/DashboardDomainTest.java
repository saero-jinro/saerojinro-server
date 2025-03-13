package dashboard;

import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DashboardDomainTest {
	@Test
	@DisplayName("of는 새로운 Dashboard를 생성한다")
	public void of_Success() {
		// given
		String TITLE = "title";
		String SPEAKER = "speaker";
		int RESERVATION = 1;
		int WISHLIST = 2;
		LocalDateTime START_TIME = LocalDateTime.now();

		// when
		Dashboard dashboard = Dashboard.of(1L, TITLE, SPEAKER, RESERVATION, WISHLIST,
			RESERVATION + WISHLIST, START_TIME);

		// then
		assertEquals(1L, dashboard.getId());
		assertEquals(TITLE, dashboard.getTitle());
		assertEquals(SPEAKER, dashboard.getSpeaker());
		assertEquals(RESERVATION, dashboard.getReservation());
		assertEquals(WISHLIST, dashboard.getWishlist());
		assertEquals(RESERVATION + WISHLIST, dashboard.getSum());
		assertEquals(START_TIME, dashboard.getStartTime());
	}
}
