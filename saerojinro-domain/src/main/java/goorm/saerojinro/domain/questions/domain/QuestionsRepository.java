package goorm.saerojinro.domain.questions.domain;

import goorm.saerojinro.domain.lecture.domain.Lecture;

import java.util.List;
import java.util.Optional;

public interface QuestionsRepository {
    List<Questions> findAll();

    List<Questions> findByLecture(Lecture lecture);

    Optional<Questions> findById(Long id);

    Questions save(Questions questions);

    void delete(Questions questions);
}
