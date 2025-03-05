package goorm.saerojinro.domain.notification.domain;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;


public interface EmitterRepository {
	SseEmitter save(Long id, SseEmitter emitter);

	Optional<SseEmitter> findById(Long id);

	List<SseEmitter> findAll();
}
