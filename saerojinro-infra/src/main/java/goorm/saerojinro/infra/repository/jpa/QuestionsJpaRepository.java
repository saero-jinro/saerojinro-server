package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.questions.domain.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionsJpaRepository extends JpaRepository<Questions, Long> {
    List<Questions> findByLecture(Lecture lecture);

}
