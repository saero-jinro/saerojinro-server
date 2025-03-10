package goorm.saerojinro.domain.eventlog.domain;

public interface EventLogProducer {
	void sendMessage(EventLogDTO eventLogDTO);
}
