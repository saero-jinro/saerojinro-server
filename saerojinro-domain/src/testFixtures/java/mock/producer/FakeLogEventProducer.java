package mock.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import goorm.saerojinro.domain.logevent.domain.dto.LogEventDto;
import goorm.saerojinro.domain.logevent.domain.LogEventProducer;

public class FakeLogEventProducer implements LogEventProducer {
	private final BlockingQueue<LogEventDto> queue = new LinkedBlockingQueue<LogEventDto>();

	@Override
	public void sendMessage(LogEventDto logEventDto) {
		queue.add(logEventDto);
	}

	public int getQueueSize() {
		return queue.size();
	}
}
