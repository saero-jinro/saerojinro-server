package goorm.saerojinro.domain.logevent.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogEventType {
	LECTURE_VIEW("강의 상세 조회"),
	LECTURE_WISHLIST("강의 즐겨찾기"),
	LECTURE_RESERVATION_SUCCESS("강의 예약 성공"),
	LECTURE_RESERVATION_FAIL("강의 예약 실패"),
	;

	private final String description;
}
