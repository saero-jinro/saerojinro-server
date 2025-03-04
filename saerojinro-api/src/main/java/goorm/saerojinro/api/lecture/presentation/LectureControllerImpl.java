package goorm.saerojinro.api.lecture.presentation;

import goorm.saerojinro.api.lecture.api.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectures")
public class LectureControllerImpl implements LectureController {

	private final LectureFacade lectureFacade;

	@Override
	@GetMapping
	public ResponseEntity<LectureListResponse> getAllLecture() {
		LectureListResponse response = lectureFacade.getAllLecture();
		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("/{lectureId}")
	public ResponseEntity<LectureDetailResponse> getByLectureId(@PathVariable Long lectureId) {
		LectureDetailResponse response = lectureFacade.getByLectureId(lectureId);
		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping(("/date"))
	public ResponseEntity<LectureListResponse> getByDate(@RequestParam("day") String day) {
		LocalDate localDate = LocalDate.parse(day);
		LectureListResponse response = lectureFacade.getByDate(localDate);
		return ResponseEntity.ok(response);
	}
}
