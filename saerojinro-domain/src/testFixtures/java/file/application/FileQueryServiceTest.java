package file.application;

import goorm.saerojinro.domain.file.application.FileQueryService;
import goorm.saerojinro.domain.file.domain.File;
import mock.repository.FakeFileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileQueryServiceTest {
	private FileQueryService fileQueryService;

	private static final String LOGICAL_NAME = "FileDomain";
	private static final String PHYSICAL_PATH = "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/sk/2024/03/18/SK007_20240318_261101.jpg";
	private static final Long FILE_SIZE = 1024L;
	private static final String EXTENSION = ".jpg";

	@BeforeEach()
	public void setUp() {
		FakeFileRepository fileRepository = new FakeFileRepository();
		fileQueryService = new FileQueryService(fileRepository);

		File file = File.create(LOGICAL_NAME, PHYSICAL_PATH, FILE_SIZE, EXTENSION);
		fileRepository.save(file);
	}

	@Test
	@DisplayName("파일 ID로 파일 조회 성공")
	void getFileById_success() {
		//given
		Long fileId = 1L;

		//when
		File savedFile = fileQueryService.getFileById(fileId);

		//then
		assertNotNull(savedFile);
		assertEquals(fileId, savedFile.getId());
		assertEquals(LOGICAL_NAME, savedFile.getLogicalName());
		assertEquals(PHYSICAL_PATH, savedFile.getPhysicalPath());
		assertEquals(FILE_SIZE, savedFile.getFileSize());
		assertEquals(EXTENSION, savedFile.getExtension());
	}
	@Test
	@DisplayName("파일 URI로 파일 조회 성공")
	void getFileByUri_success() {
		//given
		String fileUri = "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/sk/2024/03/18/SK007_20240318_261101.jpg";

		//when
		File savedFile = fileQueryService.getFileByUri(fileUri);

		//then
		assertNotNull(savedFile);
		assertEquals(PHYSICAL_PATH, savedFile.getPhysicalPath());
		assertEquals(LOGICAL_NAME, savedFile.getLogicalName());
		assertEquals(FILE_SIZE, savedFile.getFileSize());
		assertEquals(EXTENSION, savedFile.getExtension());
	}

}
