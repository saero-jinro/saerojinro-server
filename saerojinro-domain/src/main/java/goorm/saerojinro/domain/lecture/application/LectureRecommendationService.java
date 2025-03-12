package goorm.saerojinro.domain.lecture.application;

import org.springframework.stereotype.Service;

import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LectureRecommendationService {
	private final LectureRepository lectureRepository;


}
