package file;

import goorm.saerojinro.domain.file.domain.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileDomainTest {
	private static final String LOGICAL_NAME = "FileDomain";
	private static final String PHYSICAL_PATH = "https://thumbnews.nateimg.co.kr/view610///news.nateimg.co.kr/orgImg/sk/2024/03/18/SK007_20240318_261101.jpg";
	private static final Long FILE_SIZE = 1024L;
	private static final String EXTENSION = ".java";

	@Test
	public void createFile_success() {
		File file = File.create(LOGICAL_NAME, PHYSICAL_PATH, FILE_SIZE, EXTENSION);

		Assertions.assertEquals(LOGICAL_NAME, file.getLogicalName());
		Assertions.assertEquals(PHYSICAL_PATH, file.getPhysicalPath());
		Assertions.assertEquals(FILE_SIZE, file.getFileSize());
		Assertions.assertEquals(EXTENSION, file.getExtension());
	}
}
