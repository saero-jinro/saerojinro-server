package goorm.saerojinro.domain.speaker.application;

import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.speaker.domain.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpeakerCommandService {
	private final SpeakerRepository speakerRepository;

	public Speaker create(String email, String position, String introduction, String filmography, String photo) {
		return Speaker.create(email, position, introduction, filmography, photo);
	}
}
