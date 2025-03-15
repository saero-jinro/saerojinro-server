package goorm.saerojinro.domain.speaker.application;

import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.speaker.domain.SpeakerRepository;
import goorm.saerojinro.domain.speaker.exception.SpeakerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpeakerQueryService {
	private final SpeakerRepository speakerRepository;

	@Transactional(readOnly = true)
	public Speaker findById(Long speakerId) {
		return speakerRepository.findById(speakerId).orElseThrow(SpeakerNotFoundException::new);
	}
}
