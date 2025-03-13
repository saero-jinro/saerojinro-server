package goorm.saerojinro.api.speaker.presentation;

import goorm.saerojinro.api.speaker.presentation.response.SpeakerDetailResponse;
import goorm.saerojinro.domain.speaker.application.SpeakerQueryService;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/speaker")
@RequiredArgsConstructor
public class SpeakerControllerImpl implements SpeakerController {
	private final SpeakerQueryService speakerQueryService;

	@GetMapping("/{id}")
	public ResponseEntity<SpeakerDetailResponse> findById(@PathVariable Long speakerId) {
		Speaker speaker = speakerQueryService.findById(speakerId);

		return ResponseEntity.ok(SpeakerDetailResponse.from(speaker));
	}
}
