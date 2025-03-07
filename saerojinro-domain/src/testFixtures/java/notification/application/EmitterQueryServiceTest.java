package notification.application;

import goorm.saerojinro.domain.notification.application.EmitterQueryService;
import goorm.saerojinro.domain.notification.domain.EmitterRepository;
import mock.repository.FakeEmitterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmitterQueryServiceTest {
	private EmitterQueryService emitterQueryService;

	@BeforeEach
	public void init() {
		EmitterRepository emitterRepository = new FakeEmitterRepository();
		emitterQueryService = new EmitterQueryService(emitterRepository);

		emitterRepository.save(1L);
		emitterRepository.save(2L);
	}

	@Test
	@DisplayName("findById는 emitter를 조회한다")
	public void findById_Success() {
		// when
		SseEmitter emitter = emitterQueryService.findById(1L).get();

		// then
		assertNotNull(emitter);
	}

	@Test
	@DisplayName("findById는 없는 아이디를 조회하면 Optional.empty를 반환한다")
	public void findById_empty() {
		// when
		Optional<SseEmitter> emitter = emitterQueryService.findById(999L);

		// then
		assertEquals(Optional.empty(), emitter);
	}

	@Test
	@DisplayName("findAll는 emitter를 모두 조회한다")
	public void findAll_Success() {
		// when
		List<SseEmitter> all = emitterQueryService.findAll();

		// then
		assertNotNull(all);
		assertEquals(2, all.size());
	}
}
