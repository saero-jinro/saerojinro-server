package lecture.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import goorm.saerojinro.admin.api.lecture.application.LectureAdminFacade;
import goorm.saerojinro.admin.api.lecture.presentation.request.LectureCreateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.request.LectureUpdateRequest;
import goorm.saerojinro.admin.api.lecture.presentation.response.LectureCreateResponse;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.services.s3.S3Client;

import java.time.LocalDateTime;

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

	private static final String NAME = "Cole palmer";
	private static final String EMAIL = "google@mail.com";
	private static final String POSITION = "00 기업 / CEO";
	private static final String INTRODUCTION = "안녕하세요 OO 기업 CEO OOO 입니다";
	private static final String FILMOGRAPHY = "AA 기업 - 백엔드 개발 담당";

	private static final Long THUMBNAIL_FILE_ID = 1L;
	private static final Long MATERIAL_FILE_ID = 2L;
	private static final Long SPEAKER_FILE_ID = 3L;

	private static final String THUMBNAIL_PATH = "uploads/temp/thumbnail/thumbnail.jpg";
	private static final String MATERIAL_PATH = "uploads/temp/materials/material.pdf";
	private static final String SPEAKER_PATH = "uploads/temp/speaker/speaker.jpg";

	private static final String TITLE = "Title";
	private static final String CONTENTS = "Contents";

	private static final Long MAX_CAPACITY = 100L;
	private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
	private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
	private static final String LOCATION = "Location";
	private static final Category CATEGORY = Category.BACKEND;

	private LectureCreateRequest request;

	@BeforeEach
	public void setUp() {
		lectureRepository = new FakeLectureRepository();
		speakerRepository = new FakeSpeakerRepository();
		fileRepository = new FakeFileRepository();

		File thumbnailFile = File.create("thumbnail.jpg", THUMBNAIL_PATH, 5000L, "jpg");
		fileRepository.save(thumbnailFile);

		File materialFile = File.create("material.pdf", MATERIAL_PATH, 10000L, "pdf");
		fileRepository.save(materialFile);

		File speakerFile = File.create("speaker.jpg", SPEAKER_PATH, 3000L, "jpg");
		fileRepository.save(speakerFile);

		S3Client mockS3Client = mock(S3Client.class);
		fileStorageService = new FileStorageService(mockS3Client) {
			@Override
			public String moveFileDir(String originalPath, Long lectureId, String folderName) {
				// 실제 이동하지 않고, 경로만 가정
				return "uploads/" + lectureId + "/" + folderName + "/" + originalPath.substring(originalPath.lastIndexOf('/') + 1);
			}
		};
		// 필요한 bucketName 필드를 주입합니다.
		ReflectionTestUtils.setField(fileStorageService, "bucketName", "test-bucket");

		fileCommandService = new FileCommandService(fileRepository);
		fileQueryService = new FileQueryService(fileRepository);
		lectureCommandService = new LectureCommandService(lectureRepository);
		speakerCommandService = new SpeakerCommandService(speakerRepository);

		lectureAdminFacade = new LectureAdminFacade(
			lectureCommandService,
			speakerCommandService,
			fileQueryService,
			fileCommandService,
			fileStorageService
		);

		request = LectureCreateRequest.builder()
			.title(TITLE)
			.contents(CONTENTS)
			.thumbnailId(THUMBNAIL_FILE_ID)
			.materialId(MATERIAL_FILE_ID)
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
			.speakerPhotoId(SPEAKER_FILE_ID)
			.build();
	}

	@Test
	@DisplayName("정상적으로 강의를 생성한다")
	void createLecture_success() {
		// when
		LectureCreateResponse response = lectureAdminFacade.create(request);

		// then
		assertNotNull(response);
		assertTrue(response.id() > 0);

		Lecture createdLecture = lectureRepository.findById(response.id()).orElseThrow();
		assertNotNull(createdLecture.getThumbnailFile());
		assertNotNull(createdLecture.getMaterialFile());
	}

	@Test
	@DisplayName("정상적으로 강의를 수정한다")
	void updateLecture_success() {
		// given
		LectureCreateResponse createResponse = lectureAdminFacade.create(request);
		Long lectureId = createResponse.id();

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
		Long lectureId = createResponse.id();

		// when
		lectureAdminFacade.delete(lectureId);

		// then
		Lecture deletedLecture = lectureRepository.findById(lectureId)
			.orElseThrow();
		assertNotNull(deletedLecture.getDeletedAt());
	}
}
