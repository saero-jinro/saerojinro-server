package goorm.saerojinro.api.lecture.presentation;

import goorm.saerojinro.api.lecture.application.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponseByAll;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponseByDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectures")
public class LectureControllerImpl implements LectureController {

	private final LectureFacade lectureFacade;

	@Override
	@GetMapping
	public ResponseEntity<LectureListResponseByAll> getAllLecture() {
		LectureListResponseByAll response = lectureFacade.getAll();
		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<LectureDetailResponse> getByLectureId(@PathVariable Long id) {
		LectureDetailResponse response = lectureFacade.getById(id);
		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping(("/date"))
	public ResponseEntity<LectureListResponseByDate> getByDate(@RequestParam("day") String day) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		LocalDate localDate = LocalDate.parse(day, formatter);

		LectureListResponseByDate response = lectureFacade.getByDate(localDate);
		return ResponseEntity.ok(response);
	}
}
