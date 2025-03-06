package goorm.saerojinro.common.event;

import lombok.Builder;

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

	public static CommonEvent createWithUserId(EventType eventType, Long userId) {
		return CommonEvent.builder()
			.eventType(eventType)
			.userId(userId)
			.build();
	}
}
