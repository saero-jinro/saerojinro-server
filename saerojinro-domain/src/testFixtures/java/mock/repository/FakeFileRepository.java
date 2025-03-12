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
