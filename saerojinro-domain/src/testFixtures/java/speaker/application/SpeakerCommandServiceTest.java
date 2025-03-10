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

	private static final String NAME = "Cole Palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 CEO";
	private static final String INTRODUCTION = "AA 기업  - 백엔드 개발";
	private static final String FILMOGRAPHY = "Location";
	private static final String PHOTO = "Photo uri";

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
			NAME, EMAIL, POSITION, INTRODUCTION, FILMOGRAPHY, PHOTO
		);

		// then
		assertNotNull(createSpeaker);
		assertEquals(EMAIL, createSpeaker.getEmail());
		assertEquals(POSITION, createSpeaker.getPosition());
		assertEquals(INTRODUCTION, createSpeaker.getIntroduction());
		assertEquals(FILMOGRAPHY, createSpeaker.getFilmography());
		assertEquals(PHOTO, createSpeaker.getPhoto());
	}
}
