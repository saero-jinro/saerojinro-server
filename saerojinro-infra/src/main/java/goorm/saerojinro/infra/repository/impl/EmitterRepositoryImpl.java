package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	public static long TIMEOUT = 12 * 60 * 60 * 1000L;

	@Override
	public SseEmitter save(Long id) {
		Optional<SseEmitter> existingEmitter = findById(id);
		if (existingEmitter.isPresent()) {
			try {
				existingEmitter.get().send(SseEmitter.event());
				return existingEmitter.get();
			} catch (IOException e) {
				existingEmitter.get().complete();
			}
		}

		SseEmitter newEmitter = new SseEmitter(TIMEOUT);
		newEmitter.onCompletion(() -> emitters.remove(id));
		newEmitter.onTimeout(newEmitter::complete);
		emitters.put(id, newEmitter);
		return newEmitter;
	}

	@Override
	public Optional<SseEmitter> findById(Long id) {
		return Optional.ofNullable(emitters.get(id));
	}

	@Override
	public List<SseEmitter> findAll() {
		return new ArrayList<>(emitters.values());
	}
}
