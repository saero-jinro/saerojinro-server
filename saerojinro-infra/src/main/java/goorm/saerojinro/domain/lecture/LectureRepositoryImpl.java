package goorm.saerojinro.domain.lecture;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {
	private final JpaLectureRepository repository;
	@Override
	public Lecture save(Lecture lecture) {
		return repository.save(lecture);
	}

	@Override
	public List<Lecture> findAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Lecture> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	public void delete(Lecture lecture) {
		repository.delete(lecture);
	}
}
