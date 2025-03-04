package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
	List<Lecture> findByStartTime(LocalDate day);
}
