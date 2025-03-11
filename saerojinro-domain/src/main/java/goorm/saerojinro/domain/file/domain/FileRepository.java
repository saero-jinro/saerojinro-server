package goorm.saerojinro.domain.file.domain;

import java.util.Optional;

public interface FileRepository {
	File save(File file);
	Optional<File> findById(Long id);
}
