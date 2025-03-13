package goorm.saerojinro.domain.logevent.domain;

import java.util.List;

public interface LogEventRepository {
	LogEvent save(LogEvent logEvent);

	List<LogEvent> findRecentLogByUserId(Long userId);
}
