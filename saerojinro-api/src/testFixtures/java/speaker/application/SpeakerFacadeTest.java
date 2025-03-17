package speaker.application;

import goorm.saerojinro.api.speaker.application.SpeakerFacade;
import goorm.saerojinro.api.speaker.presentation.response.SpeakerDetailResponse;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.speaker.application.SpeakerQueryService;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import goorm.saerojinro.domain.speaker.domain.SpeakerRepository;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeSpeakerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SpeakerFacadeTest {
	private SpeakerFacade speakerFacade;
	private SpeakerQueryService speakerQueryService;
	private LectureQueryService lectureQueryService;
	private SpeakerRepository speakerRepository;
	private LectureRepository lectureRepository;

	@BeforeEach
	public void setUp() {
		speakerRepository = new FakeSpeakerRepository();
		lectureRepository = new FakeLectureRepository();
		speakerQueryService = new SpeakerQueryService(speakerRepository);
		lectureQueryService = new LectureQueryService(lectureRepository);
		speakerFacade = new SpeakerFacade(speakerQueryService, lectureQueryService);
	}

	@Test
	@DisplayName("강연자 id로 강의를 조회할 수 있다")
	void findById_success() {
		// given
		File dummyFile = File.builder()
			.id(1L)
			.physicalPath("dummy/path/to/image.jpg")
			.logicalName("image.jpg")
			.build();

		Speaker speaker = Speaker.builder()
			.id(1L)
			.name("Test Speaker")
			.email("test@example.com")
			.position("Speaker Position")
			.introduction("Test Introduction")
			.filmography("Test Filmography")
			.imageFile(dummyFile)
			.build();

		Lecture lecture = Lecture.builder()
			.id(101L)
			.title("Test Lecture")
			.speaker(speaker)
			.build();

		speakerRepository.save(speaker);
		lectureRepository.save(lecture);

		// when
		SpeakerDetailResponse response = speakerFacade.findById(1L);

		// then
		assertNotNull(response);
		assertEquals(response.name(), speaker.getName());
		assertEquals(response.title(), lecture.getTitle());
		assertEquals(response.speakerPhotoUrl(), dummyFile.getPhysicalPath());
	}
}
