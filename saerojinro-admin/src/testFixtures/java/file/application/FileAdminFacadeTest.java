package file.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import goorm.saerojinro.admin.api.file.application.FileAdminFacade;
import goorm.saerojinro.admin.api.file.presentation.request.FileSaveRequest;
import goorm.saerojinro.admin.api.file.presentation.response.FileSaveResponse;
import goorm.saerojinro.domain.file.application.FileCommandService;
import goorm.saerojinro.domain.file.application.FileQueryService;
import goorm.saerojinro.domain.file.application.FileStorageService;
import mock.repository.FakeFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

public class FileAdminFacadeTest {

	private FileAdminFacade fileAdminFacade;
	private FileCommandService fileCommandService;
	private FileQueryService fileQueryService;
	private FileStorageService fileStorageService;

	private RestTemplate mockRestTemplate;

	private static final String TEST_FILE_URI = "http://example.com/test.jpg";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		FakeFileRepository fakeFileRepository = new FakeFileRepository();
		fileCommandService = new FileCommandService(fakeFileRepository);
		fileQueryService = new FileQueryService(fakeFileRepository);

		fileStorageService = new FileStorageService();
		mockRestTemplate = mock(RestTemplate.class);
		ReflectionTestUtils.setField(fileStorageService, "restTemplate", mockRestTemplate);

		fileAdminFacade = new FileAdminFacade(fileCommandService, fileQueryService, fileStorageService);
	}

	@Test
	@DisplayName("강의 썸네일저장에 성공한다")
	public void saveLecturePhoto_Success() {
//		 given
		byte[] data = TEST_FILE_URI.getBytes();
		when(mockRestTemplate.getForObject(TEST_FILE_URI, byte[].class)).thenReturn(data);

		FileSaveRequest request = new FileSaveRequest(TEST_FILE_URI);

		// when
		FileSaveResponse response = fileAdminFacade.saveLecturePhoto(request);

		// then
		assertNotNull(response.id());
	}

	@Test
	@DisplayName("강의 썸네일저장에 성공한다")
	public void saveSpeakerPhoto_Success() {
//		 given
		byte[] data = TEST_FILE_URI.getBytes();
		when(mockRestTemplate.getForObject(TEST_FILE_URI, byte[].class)).thenReturn(data);

		FileSaveRequest request = new FileSaveRequest(TEST_FILE_URI);

		// when
		FileSaveResponse response = fileAdminFacade.saveSpeakerPhoto(request);

		// then
		assertNotNull(response.id());
	}

	@Test
	@DisplayName("파일 조회에 성공한다")
	public void findById_Success() {
		// given
		byte[] dummyData = "dummy image data".getBytes();
		when(mockRestTemplate.getForObject(TEST_FILE_URI, byte[].class)).thenReturn(dummyData);

		FileSaveRequest request = new FileSaveRequest(TEST_FILE_URI);
		FileSaveResponse saveResponse = fileAdminFacade.saveLecturePhoto(request);

		// when
		var readResponse = fileAdminFacade.findById(saveResponse.id());

		// then
		assertNotNull(readResponse.id());
	}

	@Test
	@DisplayName("URI에 슬래시가 없으면 전체 문자열을 반환한다")
	public void extractFileName_noSlash() {
		// given
		String uriWithoutSlash = "testfile";

		// when
		String logicalName = (String) ReflectionTestUtils.invokeMethod(fileAdminFacade, "extractFileName", uriWithoutSlash);

		// then
		assertEquals(uriWithoutSlash, logicalName);
	}
}
