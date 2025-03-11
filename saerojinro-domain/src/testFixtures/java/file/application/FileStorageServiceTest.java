package file.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import goorm.saerojinro.domain.file.application.FileStorageService;
import goorm.saerojinro.domain.file.exception.FileDownloadFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorageServiceTest {

	private FileStorageService fileStorageService;
	private RestTemplate mockRestTemplate;
	private final String testUploadDir = "test-uploads/";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		fileStorageService = new FileStorageService();

		ReflectionTestUtils.setField(fileStorageService, "uploadDir", testUploadDir);

		mockRestTemplate = mock(RestTemplate.class);
		ReflectionTestUtils.setField(fileStorageService, "restTemplate", mockRestTemplate);
	}

	@Test
	@DisplayName("uri로 사진 저장 성공")
	public void storeFileFromUri_success() throws IOException {
		String fileUri = "http://example.com/test.jpg";
		byte[] dummyData = "dummy image data".getBytes();
		when(mockRestTemplate.getForObject(fileUri, byte[].class)).thenReturn(dummyData);

		// when
		String storedPath = fileStorageService.storeFileFromUri(fileUri);

		// then
		assertNotNull(storedPath);

		Path path = Paths.get(storedPath);
		assertTrue(Files.exists(path));

		byte[] fileData = Files.readAllBytes(path);
		assertArrayEquals(dummyData, fileData);

		// 테스트 파일 삭제
		Files.deleteIfExists(path);
	}

	@Test
	@DisplayName("uri로 사진 저장 실패")
	public void storeFileFromUri_downloadFailure() {
		// given
		String fileUri = "http://example.com/nonexistent.jpg";
		when(mockRestTemplate.getForObject(fileUri, byte[].class)).thenReturn(null);

		// then
		assertThrows(FileDownloadFailedException.class, () -> fileStorageService.storeFileFromUri(fileUri));
	}

	@Test
	@DisplayName("파일 사이즈 읽기 성공")
	public void getFileSize_success() throws IOException {
		// given
		String fileName = "temp_test.txt";

		Path path = Paths.get(testUploadDir, fileName);
		Files.createDirectories(path.getParent());

		byte[] content = "Hello, world!".getBytes();
		Files.write(path, content);

		// when
		Long size = fileStorageService.getFileSize(path.toString());

		// then
		assertEquals(content.length, size);

		Files.deleteIfExists(path);
	}

	@Test
	@DisplayName("파일 확장자 읽기 성공")
	public void getFileExtension_success() {
		// given
		String storedPath = "uploads/testFile.png";

		// when
		String ext = fileStorageService.getFileExtension(storedPath);

		// then
		assertEquals("png", ext);
	}
}
