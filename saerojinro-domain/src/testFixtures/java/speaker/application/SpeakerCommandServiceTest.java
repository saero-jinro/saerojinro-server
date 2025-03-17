package speaker.application;

import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.speaker.application.SpeakerCommandService;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.speaker.domain.SpeakerRepository;
import mock.repository.FakeSpeakerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpeakerCommandServiceTest {
	private SpeakerCommandService speakerCommandService;
	private SpeakerRepository speakerRepository;

	private static final String NAME = "Cole palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 / CEO";
	private static final String INTRODUCTION = "안녕하세요 OO 기업 CEO OOO 입니다";
	private static final String FILMOGRAPHY = "AA 기업 - 백엔드 개발 담당";

	private static final String LOGICAL_NAME = "Speaker_Image";
	private static final String PHYSICAL_PATH = "uploads/speaker/123456.jpg";
	private static final Long FILE_SIZE = 3000L;
	private static final String EXTENSION = "jpg";

	private static final File SPEAKER_IMAGE_FILE = File.create(
		LOGICAL_NAME,
		PHYSICAL_PATH,
		FILE_SIZE,
		EXTENSION
	);

	@BeforeEach
	void setUp() {
		speakerRepository = new FakeSpeakerRepository();
		speakerCommandService = new SpeakerCommandService(speakerRepository);
	}

	@Test
	@DisplayName("정상적으로 강연자를 생성한다")
	void createSpeaker_success() {
		// when
		Speaker createdSpeaker = speakerCommandService.create(
			NAME, EMAIL, POSITION, INTRODUCTION, FILMOGRAPHY, SPEAKER_IMAGE_FILE
		);

		// then
		assertNotNull(createdSpeaker);
		assertEquals(EMAIL, createdSpeaker.getEmail());
		assertEquals(POSITION, createdSpeaker.getPosition());
		assertEquals(INTRODUCTION, createdSpeaker.getIntroduction());
		assertEquals(FILMOGRAPHY, createdSpeaker.getFilmography());

		assertNotNull(createdSpeaker.getImageFile());
		assertEquals(PHYSICAL_PATH, createdSpeaker.getImageFile().getPhysicalPath());
	}
}
