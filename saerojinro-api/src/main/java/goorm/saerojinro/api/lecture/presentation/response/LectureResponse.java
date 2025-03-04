package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import lombok.Builder;

@Builder
public record LectureResponse(
	String title,
	User speaker
	// TODO 썸네일 필요시 추가 ?
) {
	public static LectureResponse from(Lecture lecture) {
		return LectureResponse.builder()
			.title(lecture.getTitle())
			.speaker(lecture.getSpeaker())
			.build();
	}
}
