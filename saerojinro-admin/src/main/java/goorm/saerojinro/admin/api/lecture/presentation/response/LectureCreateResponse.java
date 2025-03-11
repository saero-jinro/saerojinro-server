package goorm.saerojinro.admin.api.lecture.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import lombok.Builder;

@Builder
public record LectureCreateResponse(
	Long speakerId,
	Long lectureId
) {
	public static LectureCreateResponse from(Speaker speaker, Lecture lecture) {
		return LectureCreateResponse.builder()
			.speakerId(speaker.getId())
			.lectureId(lecture.getId())
			.build();
	}
}
