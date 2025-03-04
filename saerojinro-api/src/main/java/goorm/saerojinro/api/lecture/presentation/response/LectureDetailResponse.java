package goorm.saerojinro.api.lecture.presentation.response;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import lombok.Builder;

@Builder
public record LectureDetailResponse(
	String title,
	String contents
	// TODO 강연자 세부 정보 ?
) {
	public static LectureDetailResponse from(Lecture lecture) {
		return LectureDetailResponse.builder()
			.title(lecture.getTitle())
			.contents(lecture.getContents())
			.build();
	}
}
