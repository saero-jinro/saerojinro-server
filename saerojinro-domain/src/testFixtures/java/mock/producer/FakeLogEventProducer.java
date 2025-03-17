package mock.producer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;
import goorm.saerojinro.domain.logevent.domain.LogEventProducer;

public class FakeLogEventProducer implements LogEventProducer {
	private final BlockingQueue<RedisLogEvent> queue = new LinkedBlockingQueue<RedisLogEvent>();

	@Override
	public void sendMessage(RedisLogEvent redisLogEvent) {
		queue.add(redisLogEvent);
	}

	public int getQueueSize() {
		return queue.size();
	}
}
