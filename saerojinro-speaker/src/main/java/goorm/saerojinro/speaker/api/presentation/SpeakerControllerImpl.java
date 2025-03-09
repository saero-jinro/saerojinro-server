import goorm.saerojinro.speaker.api.presentation.request.LectureUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

//package goorm.saerojinro.speaker.api.presentation;
//
//import goorm.saerojinro.speaker.api.application.SpeakerLectureFacade;
//import goorm.saerojinro.speaker.api.presentation.request.LectureUpdateRequest;
//import goorm.saerojinro.speaker.api.presentation.request.LectureCreateRequest;
//import goorm.saerojinro.speaker.api.presentation.response.LectureCreateResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/speakers")
//@RequiredArgsConstructor
//public class SpeakerControllerImpl implements SpeakerController {
//	private final SpeakerLectureFacade speakerLectureFacade;
//
//	@PostMapping("/{id}/lectures")
//	public ResponseEntity<LectureCreateResponse> create(@PathVariable(name = "id") Long speakerId,
//														@RequestBody LectureCreateRequest request) {
//		return ResponseEntity.ok(speakerLectureFacade.create(speakerId, request));
//	}
//
//	@PatchMapping("/{id}/lectures/{lectureId}")
//	public ResponseEntity<Void> update(@PathVariable(name = "id") Long speakerId,
//									   @PathVariable Long lectureId,
//									   @RequestBody LectureUpdateRequest request) {
//		speakerLectureFacade.update(speakerId, lectureId, request);
//		return ResponseEntity.noContent().build();
//	}
//
//
//	@DeleteMapping("/{id}/lectures/{lectureId}")
//	public ResponseEntity<Void> delete(@PathVariable(name = "id") Long speakerId,
//									   @PathVariable Long lectureId) {
//		speakerLectureFacade.delete(speakerId, lectureId);
//		return ResponseEntity.noContent().build();
//	}
//}
