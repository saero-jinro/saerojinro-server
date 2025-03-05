package goorm.saerojinro.speaker.api;

import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.speaker.presentation.request.LectureCreateRequest;
import goorm.saerojinro.speaker.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.speaker.presentation.response.LectureCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeakerLectureFacade {
	private final LectureCommandService lectureCommandService;
	private final UserQueryService userQueryService;

	public LectureCreateResponse createLecture(long speakerId, LectureCreateRequest request) {
		User speaker = userQueryService.getById(speakerId);
		Lecture lecture = lectureCommandService.createLecture(
			speaker,
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

	public void updatedLecture(Long speakerId, Long lectureId, LectureUpdateRequest request) {
		User speaker = userQueryService.getById(speakerId);
		lectureCommandService.updateLecture(
			speaker,
			lectureId,
			request.title(),
			request.contents()
		);
	}
}
