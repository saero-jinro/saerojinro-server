package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.infra.repository.jpa.LectureJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
	public Optional<Lecture> findById(Long id) {
		return lectureJpaRepository.findByIdAndDeletedAtIsNull(id);
	}

	@Override
	public Optional<Lecture> findByIdWithLock(Long id){
		return lectureJpaRepository.findByIdWithLock(id);
	}

	@Override
	public List<Lecture> findByStartTimeBetween(LocalDateTime start, LocalDateTime end){
		return lectureJpaRepository.findByStartTimeBetween(start,end);
	}

	@Override
	public void delete(Lecture lecture) {
		lectureJpaRepository.delete(lecture);
	}

	@Override
	public List<Lecture> findByStartTime(LocalDateTime time) {
		return lectureJpaRepository.findByStartTimeAndDeletedAtIsNull(time);
	}

	@Override
	public List<Lecture> findByStartTimeAfterAndEndTimeBefore(LocalDateTime startTime, LocalDateTime endTime) {
		return lectureJpaRepository.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqualAndDeletedAtIsNull(startTime, endTime);
	}

	@Override
	public List<Lecture> findByCategoryInAndStartTime(List<Category> categories, LocalDateTime lectureTime) {
		return lectureJpaRepository.findByCategoryInAndStartTimeAndDeletedAtIsNull(categories, lectureTime);
	}

	@Override
	public List<Lecture> findAll() {
		return lectureJpaRepository.findAllAndDeletedAtIsNull();
	}
}
