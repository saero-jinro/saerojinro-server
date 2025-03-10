package goorm.saerojinro.domain.eventlog.domain;

import goorm.saerojinro.common.domain.Category;

import lombok.Builder;

@Builder
public record EventLogDTO(
	Long userId,
	Long lectureId,
	EventType eventType,
	Category category
) {
	public static EventLogDTO of(Long userId, Long lectureId, EventType eventType, Category category) {
		return EventLogDTO.builder()
			.userId(userId)
			.lectureId(lectureId)
			.eventType(eventType)
			.category(category)
			.build();
	}
}
