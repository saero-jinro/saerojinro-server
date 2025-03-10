package goorm.saerojinro.domain.lecture.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureRepository {
	Lecture save(Lecture lecture);

	List<Lecture> findAll();

	Optional<Lecture> findById(Long id);

	List<Lecture> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

	void delete(Lecture lecture);

	List<Lecture> findByStartTime(LocalDateTime time);

	List<Lecture> findByStartTimeAfterAndEndTimeBefore(LocalDateTime startTime, LocalDateTime endTime);
}
