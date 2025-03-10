package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
	@Query("SELECT DISTINCT l FROM Lecture l JOIN FETCH l.speaker " +
		"WHERE l.startTime >= :start AND l.startTime < :end")
	List<Lecture> findByStartTimeBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	List<Lecture> findAll();

	Optional<Lecture> findById(@Param("id") Long id);

	List<Lecture> findByStartTime(LocalDateTime time);

	List<Lecture> findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(LocalDateTime startTime, LocalDateTime endTime);
}
