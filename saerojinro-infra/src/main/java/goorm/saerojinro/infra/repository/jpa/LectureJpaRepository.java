package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
}
