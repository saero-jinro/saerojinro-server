package goorm.saerojinro.admin.api.dashboard.application;

import goorm.saerojinro.admin.api.dashboard.presentation.response.DashboardResponse;
import goorm.saerojinro.admin.api.dashboard.presentation.response.LectureRankResponse;
import goorm.saerojinro.admin.api.dashboard.presentation.response.TimeRankResponse;
import goorm.saerojinro.domain.dashboard.application.DashboardService;
import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DashboardFacade {
	private final DashboardService dashboardService;

	public DashboardResponse getDashboard() {
		List<Dashboard> dashboardList = dashboardService.findAll();

		// sum 기준 상위 10개, 하위 10개
		List<Dashboard> high = dashboardList.stream()
			.sorted(Comparator.comparing(Dashboard::getSum).reversed())
			.limit(10)
			.toList();

		List<Dashboard> low = dashboardList.stream()
			.sorted(Comparator.comparing(Dashboard::getSum))
			.limit(10)
			.toList();

		// 시간 상위 10개
		Map<LocalDateTime, Integer> sumByStartTime = dashboardList.stream()
			.collect(
				Collectors.groupingBy(
					Dashboard::getStartTime, Collectors.summingInt(Dashboard::getSum)
				)
			);

		List<LocalDateTime> top10StartTimes = sumByStartTime.entrySet().stream()
			.sorted(Map.Entry.<LocalDateTime, Integer>comparingByValue().reversed())
			.limit(10)
			.map(Map.Entry::getKey)
			.toList();

		// Response 데이터 변환
		List<LectureRankResponse> lectureHighRank = new ArrayList<>();
		List<LectureRankResponse> lectureLowRank = new ArrayList<>();
		List<TimeRankResponse> timeRank = new ArrayList<>();

		for (int i = 0; i < high.size(); i++) {
			Dashboard dashboard = high.get(i);
			lectureHighRank.add(LectureRankResponse.from(dashboard, i + 1));
		}

		for (int i = 0; i < low.size(); i++) {
			Dashboard dashboard = low.get(i);
			lectureLowRank.add(LectureRankResponse.from(dashboard, i + 1));
		}

		for (int i = 0; i < top10StartTimes.size(); i++) {
			timeRank.add(TimeRankResponse.from(i + 1, top10StartTimes.get(i)));
		}

		return DashboardResponse.of(lectureHighRank, lectureLowRank, timeRank);
	}
}
