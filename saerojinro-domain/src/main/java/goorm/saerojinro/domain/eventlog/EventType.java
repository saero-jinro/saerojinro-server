package goorm.saerojinro.domain.eventlog;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
	LECTURE_VIEW("강의 상세 조회"),
	LECTURE_WISHLIST("강의 즐겨찾기"),
	LECTURE_REGISTER("강의 신청"),
	LECTURE_REVIEW_VIEW("강의 리뷰 조회"),
	;

	private final String description;
}
