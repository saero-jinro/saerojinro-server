package goorm.saerojinro.api.lecture.presentation;

import goorm.saerojinro.api.lecture.api.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureListResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
