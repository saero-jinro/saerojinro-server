package goorm.saerojinro.domain.lecture.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LectureRepository {
	Lecture save(Lecture lecture);

	List<Lecture> findAll();

	Optional<Lecture> findById(Long id);

	List<Lecture> findByStartDate(LocalDate day);

	void delete(Lecture lecture);
}
