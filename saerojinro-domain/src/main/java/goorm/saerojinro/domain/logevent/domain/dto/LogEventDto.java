package goorm.saerojinro.domain.logevent.domain.dto;

import goorm.saerojinro.common.domain.Category;

import goorm.saerojinro.domain.logevent.domain.enums.LogEventType;
import lombok.Builder;

@Builder
public record LogEventDto(
	Long userId,
	Long lectureId,
	LogEventType logEventType,
	Category category
) {
	public static LogEventDto of(Long userId, Long lectureId, LogEventType logEventType, Category category) {
		return LogEventDto.builder()
			.userId(userId)
			.lectureId(lectureId)
			.logEventType(logEventType)
			.category(category)
			.build();
	}
}
