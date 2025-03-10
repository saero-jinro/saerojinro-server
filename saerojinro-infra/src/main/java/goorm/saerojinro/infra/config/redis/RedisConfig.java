package goorm.saerojinro.infra.config.redis;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;

@Configuration
@EnableRedisRepositories(basePackages = "goorm.saerojinro.infra.repository.redis")
public class RedisConfig {
	@Value("${spring.data.redis.host}")
	String redisHost;

	@Value("${spring.data.redis.port}")
	int redisPort;

	@Value("${spring.data.redis.password}")
	String redisPassword;

	private static final String STREAM_KEY = "event_log_stream";
	private static final String CONSUMER_GROUP = "event_consumer_group";

	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(redisHost);
		configuration.setPort(redisPort);
		configuration.setPassword(redisPassword);

		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
			.commandTimeout(Duration.ofSeconds(5))
			.shutdownTimeout(Duration.ofSeconds(2))
			.build();

		LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration, clientConfig);
		factory.afterPropertiesSet();
		return factory;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new StringRedisSerializer());
		template.setEnableTransactionSupport(false);
		template.afterPropertiesSet();

		return template;
	}

	@Bean
	public StreamMessageListenerContainer<String, ObjectRecord<String, String>> streamMessageListenerContainer(
		RedisConnectionFactory connectionFactory,
		StreamListener<String, ObjectRecord<String, String>> streamListener) {

		StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, String>> containerOptions =
			StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
				.pollTimeout(Duration.ofSeconds(1))
				.targetType(String.class)
				.build();

		StreamMessageListenerContainer<String, ObjectRecord<String, String>> container =
			StreamMessageListenerContainer.create(connectionFactory, containerOptions);

		container.receive(
			StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed()),
			streamListener
		);

		container.start();
		return container;
	}
}
