package goorm.saerojinro.domain.speaker.application;

import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.speaker.domain.SpeakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SpeakerCommandService {
	private final SpeakerRepository speakerRepository;

	public Speaker create(String name, String email, String position, String introduction, String filmography, String photo) {
		Speaker speaker = Speaker.create(name, email, position, introduction, filmography, photo);
		return speakerRepository.save(speaker);
	}
}
