package lecture.application;

import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.admin.lecture.application.LectureAdminFacade;
import goorm.saerojinro.admin.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.lecture.presentation.response.LectureCreateResponse;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.file.application.FileCommandService;
import goorm.saerojinro.domain.file.application.FileQueryService;
import goorm.saerojinro.domain.file.application.FileStorageService;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.application.LectureCommandService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.speaker.application.SpeakerCommandService;
import mock.repository.FakeFileRepository;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeSpeakerRepository;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LectureAdminFacadeTest {

	private LectureAdminFacade lectureAdminFacade;
	private LectureCommandService lectureCommandService;
	private SpeakerCommandService speakerCommandService;
	private FileQueryService fileQueryService;
	private FileCommandService fileCommandService;
	private FileStorageService fileStorageService;

	private FakeLectureRepository lectureRepository;
	private FakeSpeakerRepository speakerRepository;
	private FakeFileRepository fileRepository;

	private static final String TITLE = "Lecture Title";
	private static final String CONTENTS = "Lecture Contents";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "room A";
	private static final Category CATEGORY = Category.BACKEND;

	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 CEO";
	private static final String INTRODUCTION = "AA 기업 - 백엔드 개발";
	private static final String FILMOGRAPHY = "Location";

	private static final String LOGICAL_NAME = "speaker_photo";
	private static final String PHYSICAL_PATH = "uploads/file_1680123456.jpg";
	private static final Long FILE_SIZE = 12345L;
	private static final String EXTENSION = "jpg";

	@BeforeEach
	public void setUp() {
		lectureRepository = new FakeLectureRepository();
		speakerRepository = new FakeSpeakerRepository();
		fileRepository = new FakeFileRepository();

		lectureCommandService = new LectureCommandService(lectureRepository);
		speakerCommandService = new SpeakerCommandService(speakerRepository);
		fileQueryService = new FileQueryService(fileRepository);
		fileCommandService = new FileCommandService(fileRepository);
		fileStorageService = new FileStorageService();

		lectureAdminFacade = new LectureAdminFacade(lectureCommandService, speakerCommandService, fileQueryService);

		File file = File.create(LOGICAL_NAME, PHYSICAL_PATH, FILE_SIZE, EXTENSION);
		fileRepository.save(file);
	}

	@Test
	@DisplayName("정상적으로 강의를 생성한다")
	void createLecture_success() {
		// given
		LectureCreateRequest request = LectureCreateRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME)
			.endTime(END_TIME)
			.location(LOCATION)
			.category(CATEGORY)
			.speakerEmail(EMAIL)
			.speakerPosition(POSITION)
			.speakerIntroduction(INTRODUCTION)
			.speakerFilmography(FILMOGRAPHY)
			.speakerPhotoUri(PHYSICAL_PATH)
			.build();

		// when
		LectureCreateResponse response = lectureAdminFacade.create(request);

		// then
		assertNotNull(response);
		assertTrue(response.lectureId() > 0);
	}

	@Test
	@DisplayName("정상적으로 강의를 수정한다")
	void updateLecture_success() {
		// given
		LectureCreateRequest createRequest = LectureCreateRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME)
			.endTime(END_TIME)
			.location(LOCATION)
			.category(CATEGORY)
			.speakerEmail(EMAIL)
			.speakerPosition(POSITION)
			.speakerIntroduction(INTRODUCTION)
			.speakerFilmography(FILMOGRAPHY)
			.speakerPhotoUri(PHYSICAL_PATH)
			.build();

		LectureCreateResponse createResponse = lectureAdminFacade.create(createRequest);
		Long lectureId = createResponse.lectureId();

		LectureUpdateRequest updateRequest = LectureUpdateRequest.builder()
			.title("Updated Title")
			.contents("Updated Contents")
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME)
			.endTime(END_TIME)
			.location(LOCATION)
			.category(CATEGORY)
			.build();

		// when
		lectureAdminFacade.update(lectureId, updateRequest);

		// then
		Lecture updatedLecture = lectureRepository.findById(lectureId).orElseThrow();
		assertEquals("Updated Title", updatedLecture.getTitle());
		assertEquals("Updated Contents", updatedLecture.getContents());
		assertEquals(MAX_CAPACITY, updatedLecture.getMaxCapacity());
		assertEquals(START_TIME, updatedLecture.getStartTime());
		assertEquals(END_TIME, updatedLecture.getEndTime());
		assertEquals(LOCATION, updatedLecture.getLocation());
		assertEquals(CATEGORY, updatedLecture.getCategory());
	}

	@Test
	@DisplayName("정상적으로 강의를 삭제한다")
	void deleteLecture_success() {
		// given
		LectureCreateRequest createRequest = LectureCreateRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME)
			.endTime(END_TIME)
			.location(LOCATION)
			.category(CATEGORY)
			.speakerEmail(EMAIL)
			.speakerPosition(POSITION)
			.speakerIntroduction(INTRODUCTION)
			.speakerFilmography(FILMOGRAPHY)
			.speakerPhotoUri(PHYSICAL_PATH)
			.build();

		LectureCreateResponse createResponse = lectureAdminFacade.create(createRequest);
		Long lectureId = createResponse.lectureId();

		// when
		lectureAdminFacade.delete(lectureId);

		// then
		Lecture deletedLecture = lectureRepository.findById(lectureId)
			.orElseThrow();
	}
}
