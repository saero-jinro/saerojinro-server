package goorm.saerojinro.speaker.api;

import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.speaker.presentation.request.LectureCreateRequest;
import goorm.saerojinro.speaker.presentation.response.LectureCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeakerLectureFacade {
	private final LectureCommandService lectureService;

	public LectureCreateResponse createLecture(long speakerId, LectureCreateRequest request) {
		// TODO 강연자 검증 로직 추가

		Lecture lecture = lectureService.createLecture(
			speakerId,
			request.title(),
			request.contents(),
			request.maxCapacity(),
			request.startTime(),
			request.endTime(),
			request.location(),
			request.category()
		);
		return LectureCreateResponse.from(lecture);
	}
}
