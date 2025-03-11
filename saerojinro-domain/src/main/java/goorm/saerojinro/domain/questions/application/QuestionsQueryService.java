package goorm.saerojinro.domain.questions.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.questions.domain.Questions;
import goorm.saerojinro.domain.questions.domain.QuestionsRepository;
import goorm.saerojinro.domain.questions.exception.QuestionsNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionsQueryService {
    private final QuestionsRepository questionsRepository;

    public List<Questions> getAll(){
        return questionsRepository.findAll();
    }

    public List<Questions> getByLecture(Lecture lecture){
        return questionsRepository.findByLecture(lecture);
    }

    public Questions getById(Long questionsId){
        return questionsRepository.findById(questionsId)
                .orElseThrow(QuestionsNotFoundException::new);
    }
}
