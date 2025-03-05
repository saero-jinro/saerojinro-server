package goorm.saerojinro.speaker.presentation;

import goorm.saerojinro.speaker.api.SpeakerLectureFacade;
import goorm.saerojinro.speaker.presentation.request.LectureCreateRequest;
import goorm.saerojinro.speaker.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.speaker.presentation.response.LectureCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/speakers")
@RequiredArgsConstructor
public class SpeakerController {
	private final SpeakerLectureFacade speakerLectureFacade;

	@PostMapping("/{id}/lectures")
	private ResponseEntity<LectureCreateResponse> createLecture(@PathVariable(name = "id") Long speakerId,
																@RequestBody LectureCreateRequest request) {
		return ResponseEntity.ok(speakerLectureFacade.createLecture(speakerId, request));
	}

	@PatchMapping("/{id}/lectures/{lectureId}")
	private ResponseEntity<Void> updateLecture(@PathVariable(name = "id") Long speakerId,
											   @PathVariable Long lectureId,
											   @RequestBody LectureUpdateRequest request) {
		speakerLectureFacade.updatedLecture(speakerId, lectureId, request);
		return ResponseEntity.noContent().build();
	}
}
