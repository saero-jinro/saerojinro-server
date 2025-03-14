package mock.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.LogEventRepository;
import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;

public class FakeLogEventRepository implements LogEventRepository {
	private final List<LogEvent> data = Collections.synchronizedList(new ArrayList<>());
	private final Map<Long, List<LogEventDto>> map = new ConcurrentHashMap<>();

	@Override
	public LogEvent save(LogEvent logEvent) {
		data.add(logEvent);
		return logEvent;
	}

	@Override
	public List<LogEvent> findRecentLogByUserId(Long userId) {
		return data.stream()
			.filter(logEvent -> logEvent.getUser().getId().equals(userId))
			.toList();
	}

	@Override
	public List<LogEventDto> findRecentFromCache(Long userId) {
		return map.getOrDefault(userId, Collections.emptyList());
	}

	public void cache(Long userId, LogEventDto logEventDto) {
		map.compute(userId, (key, logs) -> {
			if (logs == null) {
				logs = new ArrayList<>();
			} else {
				logs = new ArrayList<>(logs);
			}

			logs.add(logEventDto);

			if (logs.size() > 50) {
				logs.removeFirst();
			}

			return logs;
		});
	}
}
