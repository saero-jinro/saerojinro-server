package goorm.saerojinro.domain.lecture.domain;

import java.util.List;
import java.util.Optional;

public interface LectureRepository {
	Lecture save(Lecture lecture);

	List<Lecture> findAll();

	Optional<Lecture> findById(Long id);

	void delete(Lecture lecture);
}
