package goorm.saerojinro.admin.api.dashboard.presentation;

import goorm.saerojinro.admin.api.dashboard.application.DashboardFacade;
import goorm.saerojinro.admin.api.dashboard.presentation.response.DashboardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashboardControllerImpl implements DashboardController {
	private final DashboardFacade dashboardFacade;

	@Override
	@GetMapping
	public ResponseEntity<DashboardResponse> getDashboard() {
		DashboardResponse response = dashboardFacade.getDashboard();
		return ResponseEntity.ok(response);
	}
}
