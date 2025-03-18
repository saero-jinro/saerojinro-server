package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.question.domain.Question;
import goorm.saerojinro.domain.question.domain.QuestionRepository;
import goorm.saerojinro.infra.repository.jpa.QuestionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {
    private final QuestionJpaRepository questionJpaRepository;

    @Override
    public List<Question> findAll(){
        return questionJpaRepository.findAll();
    }

    @Override
    public List<Question> findByLectureId(Long lectureId){
        return questionJpaRepository.findByLectureId(lectureId);
    }

    @Override
    public Optional<Question> findById(Long id){
        return questionJpaRepository.findById(id);
    }


    @Override
    public Question save(Question questions) {
        return questionJpaRepository.save(questions);
    }

    @Override
    public void delete(Question questions) {
        questionJpaRepository.delete(questions);
    }

}
