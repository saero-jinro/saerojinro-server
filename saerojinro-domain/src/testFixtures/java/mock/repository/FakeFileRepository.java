package mock.repository;

import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.file.domain.FileRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeFileRepository implements FileRepository {
	private final List<File> data = Collections.synchronizedList(new ArrayList<>());
	private final AtomicLong sequence = new AtomicLong(0);

	public FakeFileRepository() {
		File speakerFile = File.builder()
			.id(sequence.incrementAndGet())
			.logicalName("Speaker_Image")
			.physicalPath("uploads/speaker/123456.jpg")
			.fileSize(3000L)
			.extension("jpg")
			.build();
		data.add(speakerFile);

		File thumbnailFile = File.builder()
			.id(sequence.incrementAndGet())
			.logicalName("Thumbnail_LogicalName")
			.physicalPath("uploads/lecture/thumbnail/123456.jpg")
			.fileSize(5000L)
			.extension("jpg")
			.build();
		data.add(thumbnailFile);

		File materialFile = File.builder()
			.id(sequence.incrementAndGet())
			.logicalName("Material_LogicalName")
			.physicalPath("uploads/lecture/materials/발표자료.pdf")
			.fileSize(10000L)
			.extension("pdf")
			.build();
		data.add(materialFile);
	}

	@Override
	public File save(File file) {
		File saved = File.builder()
			.id(sequence.incrementAndGet())
			.logicalName(file.getLogicalName())
			.physicalPath(file.getPhysicalPath())
			.fileSize(file.getFileSize())
			.extension(file.getExtension())
			.build();

		data.add(saved);
		return saved;
	}

	@Override
	public Optional<File> findById(Long id) {
		return data.stream()
			.filter(file -> file.getId().equals(id))
			.findFirst();
	}

	@Override
	public Optional<File> findByUri(String uri) {
		return data.stream()
			.filter(file -> file.getPhysicalPath().equals(uri))
			.findFirst();
	}
}
