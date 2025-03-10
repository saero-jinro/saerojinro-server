package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.speaker.domain.SpeakerRepository;
import goorm.saerojinro.infra.repository.jpa.SpeakerJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpeakerRepositoryImpl implements SpeakerRepository {
	private final SpeakerJpaRepository speakerJpaRepository;
	@Override
	public Speaker save(Speaker speaker) {
		return speakerJpaRepository.save(speaker);
	}

	@Override
	public List<Speaker> findAll() {
		return speakerJpaRepository.findAll();
	}

	@Override
	public Optional<Speaker> findById(Long id) {
		return speakerJpaRepository.findById(id);
	}
}
