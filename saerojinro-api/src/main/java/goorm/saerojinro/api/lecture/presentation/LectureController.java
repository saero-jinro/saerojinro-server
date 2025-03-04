package goorm.saerojinro.api.lecture.presentation;

import goorm.saerojinro.api.lecture.api.LectureFacade;
import goorm.saerojinro.api.lecture.presentation.response.LectureDetailResponse;
import goorm.saerojinro.api.lecture.presentation.response.LectureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@RequiredArgsConstructor
public class LectureController {
	private final LectureFacade lectureFacade;

	@GetMapping
	private ResponseEntity<List<LectureResponse>> getAllLecture() {
		List<LectureResponse> lectures = lectureFacade.getAllLecture();
		return new ResponseEntity<>(lectures, HttpStatus.OK);
	}

	@GetMapping("/{lectureId}")
	private ResponseEntity<LectureDetailResponse> getByLectureId(@PathVariable long lectureId) {
		LectureDetailResponse lecture = lectureFacade.getByLectureId(lectureId);
		return new ResponseEntity<>(lecture, HttpStatus.OK);
	}
}
