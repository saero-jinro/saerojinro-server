package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LectureResponseByDate(
	Long id,
	String title,
	Category category,
	LocalDateTime startTime,
	LocalDateTime endTime,

	//강연자
	String speakerName,
	String image
) {
	public static LectureResponseByDate from(Lecture lecture) {
		return LectureResponseByDate.builder()
			.id(lecture.getId())
			.title(lecture.getTitle())
			.category(lecture.getCategory())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.speakerName(lecture.getSpeaker().getName())
			.image(lecture.getSpeaker().getPhoto())
			.build();
	}
}
