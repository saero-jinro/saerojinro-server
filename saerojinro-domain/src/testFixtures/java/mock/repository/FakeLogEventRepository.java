package mock.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import goorm.saerojinro.domain.logevent.domain.LogEvent;
import goorm.saerojinro.domain.logevent.domain.LogEventRepository;

public class FakeLogEventRepository implements LogEventRepository {
	private final List<LogEvent> data = Collections.synchronizedList(new ArrayList<>());

	@Override
	public LogEvent save(LogEvent logEvent) {
		data.add(logEvent);
		return logEvent;
	}
}
