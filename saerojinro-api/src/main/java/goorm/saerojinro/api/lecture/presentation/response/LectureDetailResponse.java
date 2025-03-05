package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LectureDetailResponse(
	String title,
	User speaker,
	String contents,
	Category category,
	LocalDateTime startTime,
	LocalDateTime endTime,
	String location
) {
	public static LectureDetailResponse from(Lecture lecture) {
		return LectureDetailResponse.builder()
			.title(lecture.getTitle())
			.speaker(lecture.getSpeaker())
			.contents(lecture.getContents())
			.category(lecture.getCategory())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.location(lecture.getLocation())
			.build();
	}
}
