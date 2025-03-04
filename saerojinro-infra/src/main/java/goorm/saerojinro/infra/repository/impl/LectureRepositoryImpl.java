package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.infra.repository.jpa.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {
	private final LectureJpaRepository lectureJpaRepository;

	@Override
	public Lecture save(Lecture lecture) {
		return lectureJpaRepository.save(lecture);
	}

	@Override
	public List<Lecture> findAll() {
		return lectureJpaRepository.findAll();
	}

	@Override
	public Optional<Lecture> findById(Long id) {
		return lectureJpaRepository.findById(id);
	}

	@Override
	public List<Lecture> findByStartTime(LocalDate day) {
		return lectureJpaRepository.findByStartTime(day);
	}

	@Override
	public void delete(Lecture lecture) {
		lectureJpaRepository.delete(lecture);
	}
}
