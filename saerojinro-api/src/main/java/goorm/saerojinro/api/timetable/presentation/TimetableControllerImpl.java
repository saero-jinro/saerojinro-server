package goorm.saerojinro.api.timetable.presentation;

import goorm.saerojinro.api.timetable.application.TimetableFacade;
import goorm.saerojinro.api.timetable.presentation.response.TimetableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/timetables")
@PreAuthorize("hasRole('USER')")
public class TimetableControllerImpl implements TimetableController {

	private final TimetableFacade timetableFacade;

	@Override
	@GetMapping("/me")
	public ResponseEntity<TimetableResponse> getTimetable() {
		return ResponseEntity.ok(timetableFacade.getTimetable());
	}
}
