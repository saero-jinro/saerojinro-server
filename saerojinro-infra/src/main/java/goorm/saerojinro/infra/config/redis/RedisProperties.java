package goorm.saerojinro.infra.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisProperties {
	private String host;

	private int port;

	private String password;

	private final String logEventStreamKey = "log_event_stream";
}
