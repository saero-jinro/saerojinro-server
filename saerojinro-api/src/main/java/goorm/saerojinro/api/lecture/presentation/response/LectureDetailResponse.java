package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LectureDetailResponse(
	String title,
	String contents,
	Category category,
	LocalDateTime startTime,
	LocalDateTime endTime,
	String location,

	//speaker
	String speakerName,
	String speakerEmail,
	String speakerProfileImage
) {
	public static LectureDetailResponse from(Lecture lecture) {
		return LectureDetailResponse.builder()
			.title(lecture.getTitle())
			.contents(lecture.getContents())
			.category(lecture.getCategory())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.location(lecture.getLocation())
			.speakerName(lecture.getSpeaker().getName())
			.speakerEmail(lecture.getSpeaker().getEmail())
			.speakerProfileImage(lecture.getSpeaker().getProfileImage())
			.build();
	}
}
