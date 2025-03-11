package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LectureResponseByAll(
	Category category,
	String title,
	String contents,

	//speaker
	String speakerName,

	//세션 시간 및 장소
	LocalDateTime startTime,
	LocalDateTime endTime,
	String location
) {
	public static LectureResponseByAll from(Lecture lecture) {
		return LectureResponseByAll.builder()
			.category(lecture.getCategory())
			.title(lecture.getTitle())
			.contents(lecture.getContents())
			.speakerName(lecture.getSpeaker().getName())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.location(lecture.getLocation())
			.build();
	}
}
