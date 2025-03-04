package goorm.saerojinro.speaker.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.Builder;

@Builder
public record LectureCreateResponse(
	Long lectureId
) {
	public static LectureCreateResponse from(Lecture lecture) {
		return LectureCreateResponse.builder()
			.lectureId(lecture.getId())
			.build();
	}
}
