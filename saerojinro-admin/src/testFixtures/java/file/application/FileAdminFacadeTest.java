package file.application;

import goorm.saerojinro.admin.file.application.FileAdminFacade;
import goorm.saerojinro.admin.file.request.FileSaveRequest;
import goorm.saerojinro.admin.file.response.FileReadResponse;
import goorm.saerojinro.admin.file.response.FileSaveResponse;
import goorm.saerojinro.domain.file.application.FileCommandService;
import goorm.saerojinro.domain.file.application.FileQueryService;
import goorm.saerojinro.domain.file.application.FileStorageService;
import mock.repository.FakeFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileAdminFacadeTest {

	private FileAdminFacade fileAdminFacade;
	private FileCommandService fileCommandService;
	private FileQueryService fileQueryService;
	private FileStorageService fileStorageService;

	private static final String TEST_FILE_URI = "http://example.com/test.jpg";

	@BeforeEach
	public void setUp() {
		FakeFileRepository fakeFileRepository = new FakeFileRepository();
		fileCommandService = new FileCommandService(fakeFileRepository);
		fileQueryService = new FileQueryService(fakeFileRepository);
		fileStorageService = new FileStorageService();
		fileAdminFacade = new FileAdminFacade(fileCommandService, fileQueryService, fileStorageService);
	}

	@Test
	@DisplayName("파일 저장에 성공한다")
	public void saveFile_Success() {
		// given
		FileSaveRequest request = new FileSaveRequest(TEST_FILE_URI);

		// when
		FileSaveResponse response = fileAdminFacade.saveFile(request);

		// then
		assertNotNull(response.id());
		assertNotNull(response.physicalPath());
	}

	@Test
	@DisplayName("파일 조회에 성공한다")
	public void findById_Success() {
		// given
		FileSaveRequest request = new FileSaveRequest(TEST_FILE_URI);
		FileSaveResponse saveResponse = fileAdminFacade.saveFile(request);
		Long fileId = saveResponse.id();

		// when
		FileReadResponse readResponse = fileAdminFacade.findById(fileId);

		// then
		assertNotNull(readResponse.id());
		assertEquals(saveResponse.physicalPath(), readResponse.physicalPath());
	}
}
