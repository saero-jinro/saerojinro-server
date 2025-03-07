package goorm.saerojinro.domain.notification.application;

import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmitterQueryService {
	private final EmitterRepository emitterRepository;

	public Optional<SseEmitter> findById(Long receiverId) {
		return emitterRepository.findById(receiverId);
	}

	public List<SseEmitter> findAll() {
		return emitterRepository.findAll();
	}
}
