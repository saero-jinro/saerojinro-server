package goorm.saerojinro.api.lecture.presentation;

import goorm.saerojinro.api.lecture.api.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {
	private final LectureFacade lectureFacade;

	@GetMapping
	private ResponseEntity<LectureListResponse> getAllLecture() {
		LectureListResponse lectures = lectureFacade.getAllLecture();
		return ResponseEntity.ok(lectures);
	}

	@GetMapping("/{lectureId}")
	private ResponseEntity<LectureDetailResponse> getByLectureId(@PathVariable long lectureId) {
		LectureDetailResponse lecture = lectureFacade.getByLectureId(lectureId);
		return ResponseEntity.ok(lecture);
	}

	@GetMapping
	private ResponseEntity<LectureListResponse> getByDate(@RequestParam(name = "day") String dayStr) {
		LocalDate day = LocalDate.parse(dayStr);
		LectureListResponse lectures = lectureFacade.getByDate(day);
		return ResponseEntity.ok(lectures);
	}
}
