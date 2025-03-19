package goorm.saerojinro.domain.lecture.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LectureCommandService {
	private final LectureRepository lectureRepository;

	@CacheEvict(value = "lecturesByDate", key = "#startTime.toLocalDate()")
	public Lecture create(Speaker speaker, String title, String contents, File thumbnailFile, File materialFile,
						  Long maxCapacity, LocalDateTime startTime, LocalDateTime endTime, String location, Category category) {

		Lecture lecture = Lecture.create(
			speaker, title, contents, thumbnailFile, materialFile,
			maxCapacity, startTime, endTime, location, category
		);
		return lectureRepository.save(lecture);
	}

	@CacheEvict(value = "lecture", key = "#id")
	public void update(Long id, String title, String contents, Long maxCapacity,
					   LocalDateTime startTime, LocalDateTime endTime, String location, Category category) {
		Lecture lecture = lectureRepository.findById(id).orElseThrow(LectureNotFoundException::new);

		lecture.update(title, contents, maxCapacity, startTime, endTime, location, category);
	}

	@CacheEvict(value = "lecture", key = "#id")
	public void delete(Long id) {
		Lecture lecture = lectureRepository.findById(id).orElseThrow(LectureNotFoundException::new);
		lecture.delete();
	}
}
