package file.application;

import goorm.saerojinro.domain.file.application.FileCommandService;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.file.domain.FileRepository;
import mock.repository.FakeFileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class FileCommandServiceTest {
	private FileCommandService fileCommandService;

	private static final String LOGICAL_NAME = "FileDomain";
	private static final String PHYSICAL_PATH = "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/sk/2024/03/18/SK007_20240318_261101.jpg";
	private static final Long FILE_SIZE = 1024L;
	private static final String EXTENSION = ".jpg";

	@BeforeEach
	void setUp() {
		FileRepository fileRepository = new FakeFileRepository();
		fileCommandService = new FileCommandService(fileRepository);

		File file = File.create(LOGICAL_NAME, PHYSICAL_PATH, FILE_SIZE, EXTENSION);
		fileRepository.save(file);
	}

	@Test
	@DisplayName("파일 저장 성공")
	void save_success() {
		//when
		File saved = fileCommandService.save(LOGICAL_NAME, PHYSICAL_PATH, FILE_SIZE, EXTENSION);

		//then
		Assertions.assertNotNull(saved);

	}
}
