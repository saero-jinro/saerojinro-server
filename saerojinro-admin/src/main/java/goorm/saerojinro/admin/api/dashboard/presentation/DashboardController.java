package goorm.saerojinro.admin.api.dashboard.presentation;

import goorm.saerojinro.admin.api.dashboard.presentation.response.DashboardResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Dashboard", description = "대시보드 API")
public interface DashboardController {
	@Operation(summary = "대시보드 조회 API", description = """
			- Description : 이 API는 대시보드 데이터를 조회합니다.
			- Assignee : 이신행
		""")
	@ApiResponse(responseCode = "200")
	ResponseEntity<DashboardResponse> getDashboard();
}
