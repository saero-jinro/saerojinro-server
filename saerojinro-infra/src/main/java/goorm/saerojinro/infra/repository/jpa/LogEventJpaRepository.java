package goorm.saerojinro.infra.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import goorm.saerojinro.domain.logevent.domain.LogEvent;

public interface LogEventJpaRepository extends JpaRepository<LogEvent, Long> {
	List<LogEvent> findTop50ByUserIdOrderByTimestampDesc(Long userId);

	List<LogEvent> findTop20ByLectureIdInOrderByTimestampDesc(List<Long> lectureIds);
}
