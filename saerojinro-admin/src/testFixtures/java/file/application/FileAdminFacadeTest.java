package file.application;

import goorm.saerojinro.admin.file.application.FileAdminFacade;
import goorm.saerojinro.admin.file.request.FileSaveRequest;
import goorm.saerojinro.admin.file.response.FileSaveResponse;
import goorm.saerojinro.domain.file.application.FileCommandService;
import goorm.saerojinro.domain.file.application.FileStorageService;
import mock.repository.FakeFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileAdminFacadeTest {
	private FileAdminFacade fileAdminFacade;
	private FileCommandService fileCommandService;
	private FileStorageService fileStorageService;
	private final String FILE_URI = "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/sk/2024/03/18/SK007_20240318_261101.jpg";


	@BeforeEach
	public void init() {
		FakeFileRepository fakeFileRepository = new FakeFileRepository();
		fileCommandService = new FileCommandService(fakeFileRepository);
		fileStorageService = new FileStorageService();
		fileAdminFacade = new FileAdminFacade(fileCommandService, fileStorageService);
	}


	@Test
	@DisplayName("파일 저장에 성공한다.")
	public void saveFile_Success() {
		// given
		FileSaveRequest request = new FileSaveRequest(FILE_URI);

		// when
		FileSaveResponse response = fileAdminFacade.saveFile(request);

		// then
		assertNotNull(response.id());
	}
}
