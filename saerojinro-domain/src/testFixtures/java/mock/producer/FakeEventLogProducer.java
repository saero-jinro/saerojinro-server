package mock.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import goorm.saerojinro.domain.eventlog.domain.dto.EventLogDTO;
import goorm.saerojinro.domain.eventlog.domain.EventLogProducer;

public class FakeEventLogProducer implements EventLogProducer {
	private final BlockingQueue<EventLogDTO> queue = new LinkedBlockingQueue<EventLogDTO>();

	@Override
	public void sendMessage(EventLogDTO eventLogDTO) {
		queue.add(eventLogDTO);
	}

	public int getQueueSize() {
		return queue.size();
	}
}
