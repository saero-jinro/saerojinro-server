package lecture.application;

import static org.junit.jupiter.api.Assertions.*;

import goorm.saerojinro.admin.api.lecture.application.LectureAdminFacade;
import goorm.saerojinro.admin.api.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.response.LectureCreateResponse;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.file.application.FileQueryService;
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

	private FakeLectureRepository lectureRepository;
	private FakeSpeakerRepository speakerRepository;
	private FakeFileRepository fileRepository;

	private static final String NAME = "Cole palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 / CEO";
	private static final String INTRODUCTION = "안녕하세요 OO 기업 CEO OOO 입니다";
	private static final String FILMOGRAPHY = "AA 기업 - 백엔드 개발 담당";

	private static final String SPEAKER_PHOTO_URI = "uploads/speaker/123456.jpg";

	private static final String TITLE = "Title";
	private static final String CONTENTS = "Contents";

	private static final String THUMBNAIL_URI = "uploads/lecture/thumbnail/123456.jpg";
	private static final String MATERIAL_URI = "uploads/lecture/materials/발표자료.pdf";
	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "Location";
	private static final Category CATEGORY = Category.BACKEND;

	private static LectureCreateRequest request;

	@BeforeEach
	public void setUp() {
		lectureRepository = new FakeLectureRepository();
		speakerRepository = new FakeSpeakerRepository();
		fileRepository = new FakeFileRepository();

		fileQueryService = new FileQueryService(fileRepository);
		lectureCommandService = new LectureCommandService(lectureRepository);
		speakerCommandService = new SpeakerCommandService(speakerRepository);

		lectureAdminFacade = new LectureAdminFacade(lectureCommandService, speakerCommandService, fileQueryService);

		request = LectureCreateRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.thumbnailUri(THUMBNAIL_URI)
			.materialsUri(MATERIAL_URI)
			.maxCapacity(MAX_CAPACITY)
			.startTime(START_TIME)
			.endTime(END_TIME)
			.location(LOCATION)
			.category(CATEGORY)
			.speakerName(NAME)
			.speakerEmail(EMAIL)
			.speakerPosition(POSITION)
			.speakerIntroduction(INTRODUCTION)
			.speakerFilmography(FILMOGRAPHY)
			.speakerPhotoUri(SPEAKER_PHOTO_URI)
			.build();
	}

	@Test
	@DisplayName("정상적으로 강의를 생성한다")
	void createLecture_success() {
		// when
		LectureCreateResponse response = lectureAdminFacade.create(request);

		// then
		assertNotNull(response);
		assertTrue(response.lectureId() > 0);

		Lecture createdLecture = lectureRepository.findById(response.lectureId()).orElseThrow();
		assertNotNull(createdLecture.getThumbnailFile());
		assertNotNull(createdLecture.getMaterialFile());

		// FakeFileRepository가 URI를 그대로 File의 physicalPath로 설정하도록 구현했다고 가정합니다.
		assertEquals(THUMBNAIL_URI, createdLecture.getThumbnailFile().getPhysicalPath());
		assertEquals(MATERIAL_URI, createdLecture.getMaterialFile().getPhysicalPath());
	}

	@Test
	@DisplayName("정상적으로 강의를 수정한다")
	void updateLecture_success() {
		// given
		LectureCreateResponse createResponse = lectureAdminFacade.create(request);
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
		LectureCreateResponse createResponse = lectureAdminFacade.create(request);
		Long lectureId = createResponse.lectureId();

		// when
		lectureAdminFacade.delete(lectureId);

		// then
		Lecture deletedLecture = lectureRepository.findById(lectureId).orElseThrow();
		assertNotNull(deletedLecture.getDeletedAt());
	}
}
