package speaker;

import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpeakerDomainTest {
	private Speaker speaker;

	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 CEO";
	private static final String  INTRODUCTION = "AA 기업  - 백엔드 개발";
	private static final String FILMOGRAPHY = "Location";

	private static final String LOGICAL_NAME = "FileDomain";
	private static final String PHYSICAL_PATH = "http://example.com/test.jpg";
	private static final Long FILE_SIZE = 1024L;
	private static final String EXTENSION = ".java";

	private static final File file = File.builder()
		.logicalName(LOGICAL_NAME)
		.physicalPath(PHYSICAL_PATH)
		.fileSize(FILE_SIZE)
		.extension(EXTENSION)
		.build();

	@BeforeEach
	void setUp() {
		speaker = Speaker.builder()
			.email(EMAIL)
			.position(POSITION)
			.introduction(INTRODUCTION)
			.filmography(FILMOGRAPHY)
			.file(file)
			.build();
	}

	@Test
	@DisplayName("Speaker를 성공적으로 생성한다")
	void createSpeaker_success() {
		assertNotNull(speaker);
		assertEquals(EMAIL, speaker.getEmail());
		assertEquals(POSITION, speaker.getPosition());
		assertEquals(INTRODUCTION, speaker.getIntroduction());
		assertEquals(FILMOGRAPHY, speaker.getFilmography());
		assertEquals(LOGICAL_NAME, speaker.getFile().getLogicalName());
		assertEquals(EXTENSION, speaker.getFile().getExtension());
	}
}
