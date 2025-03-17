package goorm.saerojinro.domain.notification.application;

import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import goorm.saerojinro.domain.notification.exception.EmitterNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmitterQueryService {
	private final EmitterRepository emitterRepository;

	public SseEmitter findById(Long receiverId) throws EmitterNotFoundException {
		return emitterRepository.findById(receiverId)
			.orElseThrow(EmitterNotFoundException::new);
	}

	public List<SseEmitter> findAll() {
		return emitterRepository.findAll();
	}
}
