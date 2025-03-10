package mock.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import goorm.saerojinro.domain.eventlog.domain.EventLog;
import goorm.saerojinro.domain.eventlog.domain.EventLogRepository;

public class FakeEventLogRepository implements EventLogRepository {
	private final List<EventLog> data = Collections.synchronizedList(new ArrayList<>());

	@Override
	public EventLog save(EventLog eventLog) {
		data.add(eventLog);
		return eventLog;
	}
}
