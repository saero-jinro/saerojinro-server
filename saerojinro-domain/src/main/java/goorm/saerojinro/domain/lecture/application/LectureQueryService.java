package goorm.saerojinro.domain.lecture.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.lecture.exception.LectureNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureQueryService {
	private final LectureRepository lectureRepository;

	public List<Lecture> getAllLecture() {
		return lectureRepository.findAll();
	}

	public Lecture getByLectureId(Long lectureId) {
		return lectureRepository.findById(lectureId).orElseThrow(LectureNotFoundException::new);
	}
}
