package speaker.application;

import goorm.saerojinro.domain.speaker.application.SpeakerCommandService;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.speaker.domain.SpeakerRepository;
import mock.repository.FakeSpeakerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpeakerCommandServiceTest {
	private SpeakerCommandService speakerCommandService;
	private SpeakerRepository speakerRepository;

	private static final String NAME = "Cole palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 / CEO";
	private static final String INTRODUCTION = "안녕하세요 OO 기업 CEO OOO 입니다";
	private static final String FILMOGRAPHY = "AA 기업 - 백엔드 개발 담당";
	private static final String IMAGE_URI = "uploads/speaker";

	@BeforeEach
	void setUp() {
		speakerRepository = new FakeSpeakerRepository();
		speakerCommandService = new SpeakerCommandService(speakerRepository);
	}

	@Test
	@DisplayName("정상적으로 강연자를 생성한다")
	void createLecture_success() {
		// when
		Speaker createSpeaker = speakerCommandService.create(
			NAME, EMAIL, POSITION, INTRODUCTION, FILMOGRAPHY, IMAGE_URI
		);

		// then
		assertNotNull(createSpeaker);
		assertEquals(EMAIL, createSpeaker.getEmail());
		assertEquals(POSITION, createSpeaker.getPosition());
		assertEquals(INTRODUCTION, createSpeaker.getIntroduction());
		assertEquals(FILMOGRAPHY, createSpeaker.getFilmography());
		assertEquals(IMAGE_URI, createSpeaker.getImageUri());
	}
}
