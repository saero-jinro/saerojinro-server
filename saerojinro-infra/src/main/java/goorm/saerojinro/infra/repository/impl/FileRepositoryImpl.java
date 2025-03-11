package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.file.domain.FileRepository;
import goorm.saerojinro.infra.repository.jpa.FileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class FileRepositoryImpl implements FileRepository {
	private final FileJpaRepository fileJpaRepository;

	@Override
	public File save(File file) {
		return fileJpaRepository.save(file);
	}

	@Override
	public Optional<File> findById(Long id) {
		return fileJpaRepository.findById(id);
	}
}
