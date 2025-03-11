package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.file.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileJpaRepository extends JpaRepository<File, Long> {

}
