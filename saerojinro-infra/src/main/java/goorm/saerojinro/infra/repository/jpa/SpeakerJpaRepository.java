package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.speaker.domain.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpeakerJpaRepository extends JpaRepository<Speaker, Long> {
}
