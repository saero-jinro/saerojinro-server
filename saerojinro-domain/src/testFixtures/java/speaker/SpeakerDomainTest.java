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

	private static final String NAME = "Cole palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 / CEO";
	private static final String INTRODUCTION = "안녕하세요 OO 기업 CEO OOO 입니다";
	private static final String FILMOGRAPHY = "AA 기업 - 백엔드 개발 담당";

	private static final String LOGICAL_NAME = "2025 상반기 신입 채용";
	private static final String PHYSICAL_PATH = "uploads/speaker";
	private static final Long FILE_SIZE = 10000L;
	private static final String EXTENSION = "pdf";

	File file = File.create(
		LOGICAL_NAME,
		PHYSICAL_PATH,
		FILE_SIZE,
		EXTENSION
	);

	@BeforeEach
	void setUp() {
		speaker = Speaker.builder()
			.name(NAME)
			.email(EMAIL)
			.position(POSITION)
			.introduction(INTRODUCTION)
			.filmography(FILMOGRAPHY)
			.imageFile(file)
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
		assertEquals("uploads/speaker", speaker.getImageFile().getPhysicalPath());
	}
}
