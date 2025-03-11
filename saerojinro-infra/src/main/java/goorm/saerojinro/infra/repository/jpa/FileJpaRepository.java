package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileJpaRepository extends JpaRepository<File, Long> {
	Optional<File> findByPhysicalPath(String uri);
}
