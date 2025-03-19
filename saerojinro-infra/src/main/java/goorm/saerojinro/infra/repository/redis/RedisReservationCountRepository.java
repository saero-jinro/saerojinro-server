package goorm.saerojinro.infra.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisReservationCountRepository {
	private final RedisTemplate<String, String> redisTemplate;
	private static final String KEY_PREFIX = "reservation:";

	public Integer findByLectureId(Long lectureId) {
		String s = redisTemplate.opsForValue()
			.get(KEY_PREFIX + lectureId);
		if (s == null) {
			throw new NullPointerException();
		}
		return Integer.parseInt(s);
	}

	public void updateCurrentReservation(Long lectureId, Integer reservationNumber) {
		redisTemplate.opsForValue().set(KEY_PREFIX + lectureId, reservationNumber.toString());
	}
}
