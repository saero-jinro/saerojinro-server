package goorm.saerojinro.domain.logevent.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LogEventType {
	LECTURE_VIEW("강의 상세 조회", 2),
	LECTURE_WISHLIST("강의 즐겨찾기", 5),
	LECTURE_RESERVATION_SUCCESS("강의 예약 성공", 10),
	LECTURE_RESERVATION_FAIL("강의 예약 실패", 6),
	;

	private final String description;
	private final int weight;
}
