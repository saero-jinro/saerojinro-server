package goorm.saerojinro.domain.logevent.domain;

import java.util.List;

import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;

public interface LogEventRepository {
	LogEvent save(LogEvent logEvent);

	List<LogEvent> findRecentLogByUserId(Long userId);

	List<LogEventDto> findRecentFromCache(Long userId);

	void cache(LogEventDto logEventDto);
}
