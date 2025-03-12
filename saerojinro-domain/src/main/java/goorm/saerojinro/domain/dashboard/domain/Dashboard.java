package goorm.saerojinro.domain.dashboard.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "dashboard", timeToLive = 60 * 60 * 24)
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {
	@Id
	private Long id;

	private int reservation;

	private int wishlist;

	private int sum;
}
