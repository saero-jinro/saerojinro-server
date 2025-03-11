package goorm.saerojinro.api.timetable.presentation;

import goorm.saerojinro.api.timetable.presentation.response.TimetableResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendees")
public class TimetableControllerImpl implements TimetableController {

	@Override
	@GetMapping("/{id}/timetable")
	public ResponseEntity<TimetableResponse> getTimetable(@PathVariable("id") Long attendeeId) {
		// TODO
		return null;
	}
}
