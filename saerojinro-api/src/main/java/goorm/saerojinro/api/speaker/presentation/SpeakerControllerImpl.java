package goorm.saerojinro.api.speaker.presentation;

import goorm.saerojinro.api.speaker.application.SpeakerFacade;
import goorm.saerojinro.api.speaker.presentation.response.SpeakerDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/speaker")
@RequiredArgsConstructor
public class SpeakerControllerImpl implements SpeakerController {
	private final SpeakerFacade speakerFacade;

	@GetMapping("/{id}")
	public ResponseEntity<SpeakerDetailResponse> findById(@PathVariable(value = "id") Long speakerId) {
		SpeakerDetailResponse response = speakerFacade.findById(speakerId);
		return ResponseEntity.ok(response);
	}
}
