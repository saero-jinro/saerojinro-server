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

		mockRestTemplate = mock(RestTemplate.class);
		ReflectionTestUtils.setField(fileStorageService, "restTemplate", mockRestTemplate);
	}

	@Test
	@DisplayName("URI로 강의 썸네일사진 저장 성공")
	public void storeFileFromUri_success() throws IOException {
		String baseDir = "lecture/";
		String fileUri = "http://example.com/test.jpg";
		byte[] dummyData = "dummy image data".getBytes();
		when(mockRestTemplate.getForObject(anyString(), eq(byte[].class))).thenReturn(dummyData);

		// when
		String storedPath = fileStorageService.storeFileFromUri(fileUri, baseDir);

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
	@DisplayName("URI로 사진 저장 실패 - 다운로드 실패 시 예외 발생")
	public void storeFileFromUri_downloadFailure() {
		String baseDir = "lecture/";
		String fileUri = "http://example.com/nonexistent.jpg";
		when(mockRestTemplate.getForObject(anyString(), eq(byte[].class))).thenReturn(null);

		assertThrows(FileDownloadFailedException.class, () ->
			fileStorageService.storeFileFromUri(fileUri, baseDir)
		);
	}

	@Test
	@DisplayName("파일 사이즈 읽기 성공")
	public void getFileSize_success() throws IOException {
		// 임시 디렉토리를 생성해 테스트
		Path tempDir = Files.createTempDirectory("test-uploads");
		String fileName = "temp_test.txt";
		Path path = tempDir.resolve(fileName);
		Files.createDirectories(path.getParent());

		byte[] content = "Hello, world!".getBytes();
		Files.write(path, content);

		Long size = fileStorageService.getFileSize(path.toString());
		assertEquals(content.length, size);

		Files.deleteIfExists(path);
		Files.deleteIfExists(tempDir);
	}

	@Test
	@DisplayName("파일 확장자 읽기 성공")
	public void getFileExtension_success() {
		String storedPath = "uploads/testFile.png";
		String ext = fileStorageService.getFileExtension(storedPath);
		assertEquals("png", ext);
	}

	@Test
	@DisplayName("쿼리 파라미터 제거 후 확장자 추출 성공")
	public void extractExtension_withQueryParameters() {
		String fileUri = "http://example.com/image.jpg?rnd=12345";
		// private 메서드 호출
		String extension = (String) ReflectionTestUtils.invokeMethod(fileStorageService, "extractExtension", fileUri);
		assertEquals(".jpg", extension);
	}

	@Test
	@DisplayName("URL 인코딩 및 괄호 강제 인코딩 테스트")
	public void encodeUrl_test() {
		String fileUri = "https://cdn.prod.website-files.com/62fe3f004c3de985c9e10052/675c53caf2ec0f9824652ac2_GettyImages-2189034330%20(1).jpg";
		String encodedUrl = (String) ReflectionTestUtils.invokeMethod(fileStorageService, "encodeUrl", fileUri);
		// 괄호가 인코딩되었는지 검증
		assertTrue(encodedUrl.contains("%28"));
		assertTrue(encodedUrl.contains("%29"));
	}
}
