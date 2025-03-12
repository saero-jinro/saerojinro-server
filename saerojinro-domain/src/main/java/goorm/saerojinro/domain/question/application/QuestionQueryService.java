package goorm.saerojinro.domain.question.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.question.domain.Question;
import goorm.saerojinro.domain.question.domain.QuestionRepository;
import goorm.saerojinro.domain.question.exception.QuestionNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionQueryService {
    private final QuestionRepository questionRepository;

    public List<Question> getAll(){
        return questionRepository.findAll();
    }

    public List<Question> getByLecture(Lecture lecture){
        return questionRepository.findByLecture(lecture);
    }

    public Question getById(Long questionsId){
        return questionRepository.findById(questionsId)
                .orElseThrow(QuestionNotFoundException::new);
    }
}
