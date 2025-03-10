package goorm.saerojinro.common.event;

import lombok.Builder;

import static goorm.saerojinro.common.event.EventType.BROADCAST_NOTICE;

@Builder
public record CommonEvent(
	EventType eventType,
	Long lectureId,
	Long userId,
	String title,
	String contents
) {
	public static CommonEvent createWithLectureId(EventType eventType, Long lectureId, String title, String contents) {
		return CommonEvent.builder()
			.eventType(eventType)
			.lectureId(lectureId)
			.title(title)
			.contents(contents)
			.build();
	}

	public static CommonEvent createBroadcast(String title, String contents) {
		return CommonEvent.builder()
			.eventType(BROADCAST_NOTICE)
			.title(title)
			.contents(contents)
			.build();
	}
}
