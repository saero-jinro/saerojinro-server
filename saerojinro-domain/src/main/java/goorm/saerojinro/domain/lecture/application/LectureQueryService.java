package goorm.saerojinro.domain.lecture.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LectureQueryService {
	private final LectureRepository lectureRepository;

	public List<Lecture> getAll() {
		return lectureRepository.findAll();
	}

	public Lecture getById(Long lectureId) {
		return lectureRepository.findById(lectureId)
			.orElseThrow(LectureNotFoundException::new);
	}

	public Lecture getByIdWithLock(Long lectureId){
		return lectureRepository.findByIdWithLock(lectureId)
				.orElseThrow(LectureNotFoundException::new);
	}

	public List<Lecture> getByDate(LocalDate localDate) {
		LocalDateTime start = localDate.atStartOfDay();
		LocalDateTime end = localDate.plusDays(1).atStartOfDay();
		return lectureRepository.findByStartTimeBetween(start, end);
	}

	public List<Lecture> getAllLectureByStartTime(LocalDateTime time) {
		return lectureRepository.findByStartTime(time);
	}

	public List<Lecture> getAllLectureBetween(LocalDateTime startTime, LocalDateTime endTime) {
		return lectureRepository.findByStartTimeAfterAndEndTimeBefore(startTime, endTime);
	}

	public List<Lecture> getRecommendedLectureByDate(Map<Category, Integer> categoryPriorityMap, LocalDateTime lectureTime) {
		List<Category> categories = new ArrayList<>(categoryPriorityMap.keySet());

		List<Lecture> lectures = lectureRepository.findByCategoryInAndStartTime(categories, lectureTime);

		return lectures.stream()
			.sorted(Comparator.comparing(lecture -> categoryPriorityMap.getOrDefault(lecture.getCategory(), Integer.MAX_VALUE)))
			.toList();
	}

	public Lecture getBySpeakerId(Long id) {
		return lectureRepository.findBySpeakerId(id).orElseThrow(LectureNotFoundException::new);
	}
}
