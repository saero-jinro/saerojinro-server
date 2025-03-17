package goorm.saerojinro.api.speaker.application;

import goorm.saerojinro.api.speaker.presentation.response.SpeakerDetailResponse;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.application.SpeakerQueryService;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class SpeakerFacade {
	private final SpeakerQueryService speakerQueryService;

	@Transactional(readOnly = true)
	public SpeakerDetailResponse findById(Long speakerId) {
		Speaker speaker = speakerQueryService.findById(speakerId);
		Lecture lecture = speaker.getLecture();
		return SpeakerDetailResponse.from(speaker, lecture);
	}
}


