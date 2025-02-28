package goorm.saerojinro.domain.lecture;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLectureRepository extends JpaRepository<Lecture, Long> {
}
