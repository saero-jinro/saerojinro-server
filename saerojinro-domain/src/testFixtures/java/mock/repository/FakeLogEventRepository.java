package mock.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.LogEventRepository;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;

public class FakeLogEventRepository implements LogEventRepository {
	private final List<LogEvent> data = Collections.synchronizedList(new ArrayList<>());
	private final Map<Long, List<RedisLogEvent>> map = new ConcurrentHashMap<>();

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
	public List<RedisLogEvent> findRecentFromRedis(Long userId) {
		return map.getOrDefault(userId, Collections.emptyList());
	}

	@Override
	public void cache(RedisLogEvent redisLogEvent) {
		map.compute(redisLogEvent.userId(), (userId, logList) -> {
			if (logList == null) {
				logList = new ArrayList<>();
			}

			logList.add(redisLogEvent);
			return logList;
		});
	}

	@Override
	public List<LogEvent> findTop20ByLectureIdInOrderByTimestampDesc(List<Long> lectureIds) {
		return data.stream()
			.filter(logEvent -> lectureIds.contains(logEvent.getLecture().getId()))
			.sorted(Comparator.comparing(LogEvent::getTimestamp).reversed())
			.limit(20)
			.toList();
	}
}
