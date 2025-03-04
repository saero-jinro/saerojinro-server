package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import org.springframework.stereotype.Repository;
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
	public SseEmitter save(Long id, SseEmitter emitter) {
		emitter.onCompletion(() -> emitters.remove(id));
		emitter.onTimeout(emitter::complete);
		emitters.put(id, emitter);
		return emitter;
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
