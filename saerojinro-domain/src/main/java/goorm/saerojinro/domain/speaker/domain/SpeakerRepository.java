package goorm.saerojinro.domain.speaker.domain;

import java.util.List;
import java.util.Optional;

public interface SpeakerRepository {
	Speaker save(Speaker speaker);

	List<Speaker> findAll();

	Optional<Speaker> findById(Long id);
}
