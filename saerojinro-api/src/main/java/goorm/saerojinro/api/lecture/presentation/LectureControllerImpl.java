package goorm.saerojinro.api.lecture.presentation;

import goorm.saerojinro.api.lecture.application.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureSummaryListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lectures")
public class LectureControllerImpl implements LectureController {
	private final LectureFacade lectureFacade;

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<LectureDetailResponse> getByLectureId(@PathVariable Long id) {
		LectureDetailResponse response = lectureFacade.getById(id);
		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("/date")
	public ResponseEntity<LectureListResponse> getByDate(@RequestParam("date") LocalDate date) {
		LectureListResponse response = lectureFacade.getByDate(date);
		return ResponseEntity.ok(response);
	}

	@Override
	@GetMapping("/recommendations")
	public ResponseEntity<LectureSummaryListResponse> getRecommendationLectures(LocalDateTime lectureStartTime) {
		LectureSummaryListResponse response = lectureFacade.getRecommendationLectures(lectureStartTime);
		return ResponseEntity.ok(response);
	}
}
