package goorm.saerojinro.domain.dashboard.domain;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Builder
@RedisHash(value = "dashboard", timeToLive = 60 * 60 * 24)
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {
	@Id
	private Long id;

	private String title;

	private String speaker;

	private int reservation;

	private int wishlist;

	private int sum;

	private LocalDateTime startTime;

	public static Dashboard of(Long id, String title, String speaker,
							   int reservation, int wishlist, int sum, LocalDateTime startTime) {
		return Dashboard.builder()
			.id(id)
			.title(title)
			.speaker(speaker)
			.reservation(reservation)
			.wishlist(wishlist)
			.sum(sum)
			.startTime(startTime)
			.build();
	}

	public static Dashboard from(Dashboard dashboard, int reservation, int wishlist, int sum) {
		return Dashboard.builder()
			.id(dashboard.getId())
			.title(dashboard.getTitle())
			.speaker(dashboard.getSpeaker())
			.reservation(reservation)
			.wishlist(wishlist)
			.sum(sum)
			.startTime(dashboard.getStartTime())
			.build();
	}
}
