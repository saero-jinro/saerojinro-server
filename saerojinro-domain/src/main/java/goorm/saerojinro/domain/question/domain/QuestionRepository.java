package goorm.saerojinro.domain.question.domain;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    List<Question> findAll();

    List<Question> findByLectureId(Long lectureId);

    Optional<Question> findById(Long id);

    Question save(Question questions);

    void delete(Question questions);
}
