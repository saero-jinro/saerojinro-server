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

		Lecture lecture = Lecture.create(speaker, title, contents, maxCapacity, startTime, endTime, location, category);
		return lectureRepository.save(lecture);
	}

	public void update(User speaker, Long lectureId, String title, String contents) {
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(LectureNotFoundException::new);

		lecture.update(speaker, title, contents);
	}

	public void delete(User speaker, Long lectureId) {
		Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(LectureNotFoundException::new);
		lecture.requestDelete(speaker);
	}
}
