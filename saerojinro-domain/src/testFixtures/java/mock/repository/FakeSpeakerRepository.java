package mock.repository;

import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.speaker.domain.SpeakerRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeSpeakerRepository implements SpeakerRepository {
	private final List<Speaker> data = Collections.synchronizedList(new ArrayList<>());
	private final AtomicLong sequence = new AtomicLong(0);

	@Override
	public Speaker save(Speaker speaker) {
		Speaker saved = Speaker.builder()
			.id(sequence.getAndIncrement())
			.email(speaker.getEmail())
			.position(speaker.getPosition())
			.introduction(speaker.getIntroduction())
			.filmography(speaker.getFilmography())
			.photo(speaker.getPhoto())
			.build();
		data.add(saved);
		return saved;
	}

	@Override
	public List<Speaker> findAll() {
		return new ArrayList<>(data);
	}

	@Override
	public Optional<Speaker> findById(Long id) {
		return data.stream()
			.filter(speaker -> speaker.getId().equals(id))
			.findFirst();
	}
}
