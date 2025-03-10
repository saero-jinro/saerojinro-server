package goorm.saerojinro.domain.logevent.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogEventType {
	LECTURE_VIEW("강의 상세 조회"),
	LECTURE_WISHLIST("강의 즐겨찾기"),
	LECTURE_REGISTER("강의 신청"),
	;

	private final String description;
}
