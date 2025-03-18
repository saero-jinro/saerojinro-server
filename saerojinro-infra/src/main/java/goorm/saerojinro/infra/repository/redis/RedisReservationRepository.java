package goorm.saerojinro.infra.repository.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisReservationRepository {
	private final RedisTemplate<String, Integer> redisTemplate;
	private static final String KEY_PREFIX = "reservation:";

	public Integer findByLectureId(Long lectureId){
		return redisTemplate.opsForValue()
			.get(KEY_PREFIX + lectureId);
	}

	public void updateCurrentReservation(Long lectureId, Integer reservationNumber){
		redisTemplate.opsForValue().set(KEY_PREFIX + lectureId, reservationNumber);
	}
}
