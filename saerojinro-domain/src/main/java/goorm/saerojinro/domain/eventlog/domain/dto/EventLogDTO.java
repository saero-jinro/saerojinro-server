package goorm.saerojinro.domain.eventlog.domain.dto;

import goorm.saerojinro.common.domain.Category;

import goorm.saerojinro.domain.eventlog.domain.EventLogType;
import lombok.Builder;

@Builder
public record EventLogDTO(
	Long userId,
	Long lectureId,
	EventLogType eventLogType,
	Category category
) {
	public static EventLogDTO of(Long userId, Long lectureId, EventLogType eventLogType, Category category) {
		return EventLogDTO.builder()
			.userId(userId)
			.lectureId(lectureId)
			.eventLogType(eventLogType)
			.category(category)
			.build();
	}
}
