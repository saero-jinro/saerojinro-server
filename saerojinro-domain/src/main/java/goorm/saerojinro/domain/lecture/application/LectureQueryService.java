package goorm.saerojinro.domain.lecture.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureQueryService {
	private final LectureRepository lectureRepository;

	public List<Lecture> getAllLecture() {
		return lectureRepository.findAll();
	}

	public Lecture getByLectureId(Long lectureId) {
		return lectureRepository.findById(lectureId).orElseThrow(LectureNotFoundException::new);
	}

	public List<Lecture> getByDate(LocalDate localDate) {
		LocalDateTime start = localDate.atStartOfDay();
		LocalDateTime end   = localDate.plusDays(1).atStartOfDay();
		return lectureRepository.findByStartTimeBetween(start, end);
	}
}
