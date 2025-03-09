package goorm.saerojinro.domain.lecture.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class LectureCommandService {
	private final LectureRepository lectureRepository;

	public Lecture create(User speaker, String title, String contents, Long maxCapacity,
						  LocalDateTime startTime, LocalDateTime endTime, String location, Category category) {

		Lecture lecture = Lecture.create(null, title, contents, maxCapacity, startTime, endTime, location, category);
		return lectureRepository.save(lecture);
	}

	public void update(Long id, String title, String contents, Long maxCapacity,
					   LocalDateTime startTime, LocalDateTime endTime, String location, Category category) {
		Lecture lecture = lectureRepository.findById(id).orElseThrow(LectureNotFoundException::new);

		lecture.update(title, contents, maxCapacity, startTime, endTime, location, category);
	}

	public void delete(Long id) {
		Lecture lecture = lectureRepository.findById(id).orElseThrow(LectureNotFoundException::new);
		lecture.delete();
	}
}
