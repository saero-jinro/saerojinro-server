package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.questions.domain.Questions;
import goorm.saerojinro.domain.questions.domain.QuestionsRepository;
import goorm.saerojinro.infra.repository.jpa.QuestionsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QuestionsRepositoryImpl implements QuestionsRepository {
    private final QuestionsJpaRepository questionsJpaRepository;

    @Override
    public List<Questions> findAll(){
        return questionsJpaRepository.findAll();
    }

    @Override
    public List<Questions> findByLecture(Lecture lecture){
        return questionsJpaRepository.findByLecture(lecture);
    }

    @Override
    public Optional<Questions> findById(Long id){
        return questionsJpaRepository.findById(id);
    }


    @Override
    public Questions save(Questions questions) {
        return questionsJpaRepository.save(questions);
    }

    @Override
    public void delete(Questions questions) {
        questionsJpaRepository.delete(questions);
    }

}
