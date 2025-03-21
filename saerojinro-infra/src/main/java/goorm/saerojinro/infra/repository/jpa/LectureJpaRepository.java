package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
	@Query("SELECT l FROM Lecture l JOIN FETCH l.speaker " +
		"WHERE l.startTime >= :start AND l.startTime < :end AND l.deletedAt IS NULL")
	List<Lecture> findByStartTimeBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

	List<Lecture> findAllByDeletedAtIsNull();

	Optional<Lecture> findByIdAndDeletedAtIsNull(@Param("id") Long id);

	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("SELECT l FROM Lecture l WHERE l.id = :id AND l.deletedAt IS NULL")
	Optional<Lecture> findByIdWithLock(@Param("id") Long id);

	List<Lecture> findByStartTimeAndDeletedAtIsNull(LocalDateTime time);

	List<Lecture> findByStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndDeletedAtIsNull(LocalDateTime startTime, LocalDateTime endTime);

	List<Lecture> findByCategoryInAndStartTimeAndDeletedAtIsNull(List<Category> categories, LocalDateTime lectureTime);
}
