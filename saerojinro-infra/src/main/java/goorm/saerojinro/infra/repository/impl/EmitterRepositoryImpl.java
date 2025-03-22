package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {
	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	@Override
	public SseEmitter save(Long id) {
		Optional<SseEmitter> emitter = findById(id);
		emitter.ifPresent(ResponseBodyEmitter::complete);

		SseEmitter newEmitter = new SseEmitter(12 * 60 * 60 * 1000L);
		newEmitter.onCompletion(() -> emitters.remove(id));
		newEmitter.onTimeout(newEmitter::complete);
		return emitters.put(id, newEmitter);
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
