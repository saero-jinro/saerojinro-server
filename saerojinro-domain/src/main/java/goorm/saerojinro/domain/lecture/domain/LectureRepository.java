package goorm.saerojinro.domain.lecture.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import goorm.saerojinro.common.domain.Category;

public interface LectureRepository {
	Lecture save(Lecture lecture);

	Optional<Lecture> findById(Long id);

	Optional<Lecture> findByIdWithLock(Long id);

	List<Lecture> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

	void delete(Lecture lecture);

	List<Lecture> findByStartTime(LocalDateTime time);

	List<Lecture> findByStartTimeAfterAndEndTimeBefore(LocalDateTime startTime, LocalDateTime endTime);

	List<Lecture> findByCategoryInAndStartTime(List<Category> categories, LocalDateTime lectureTime);

	List<Lecture> findAll();

	Optional<Lecture> findBySpeakerId(Long id);
}
