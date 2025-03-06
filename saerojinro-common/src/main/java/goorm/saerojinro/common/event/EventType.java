package goorm.saerojinro.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
	BROADCAST_NOTICE("전체 공지"),
	LECTURE_NOTICE("강의 공지"),
	SPEAKER_CREATE("강연자 권한 요청"),
	SPEAKER_APPROVED("강연자 권한 승인"),
	LECTURE_CREATE("강의 승인 요청"),
	LECTURE_APPROVE("강의 승인 완료"),
	;

	private final String description;
}
