package goorm.saerojinro.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
	BROADCAST_NOTICE("전체 공지"),
	LECTURE_NOTICE("강의 공지"),
	LECTURE_IMMINENT("강의 임박 공지"),
	;

	private final String description;
}
