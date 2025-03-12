package goorm.saerojinro.domain.dashboard.application;

import goorm.saerojinro.domain.dashboard.domain.Dashboard;
import goorm.saerojinro.domain.dashboard.domain.DashboardRepository;
import goorm.saerojinro.domain.dashboard.dto.DashboardAggregation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
	private final DashboardRepository dashboardRepository;

	public DashboardAggregation findAll() {
		List<Dashboard> dashboardList = dashboardRepository.findAll();

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
		List<LocalDateTime> times = dashboardList.stream()
			.collect(
				Collectors.groupingBy(Dashboard::getStartTime, Collectors.summingInt(Dashboard::getSum))
			).entrySet().stream()
			.sorted(Map.Entry.<LocalDateTime, Integer>comparingByValue().reversed())
			.limit(10)
			.map(Map.Entry::getKey)
			.toList();

		// 데이터 집계
		return new DashboardAggregation(high, low, times);
	}

	public Dashboard save(Dashboard dashboard) {
		return dashboardRepository.save(dashboard);
	}
}
