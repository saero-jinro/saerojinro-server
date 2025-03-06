package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.Builder;

@Builder
public record LectureResponse(
	String title,
	Category category,

	//speaker
	String speakerName
) {
	public static LectureResponse from(Lecture lecture) {
		return LectureResponse.builder()
			.title(lecture.getTitle())
			.category(lecture.getCategory())
			.speakerName(lecture.getSpeaker().getName())
			.build();
	}
}
