package goorm.saerojinro.domain.question.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.question.domain.Question;
import goorm.saerojinro.domain.question.domain.QuestionRepository;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionCommandService {
    private final QuestionRepository questionsRepository;

    public Question create(User user, Lecture lecture, String content){
        Question questions = Question.create(user, lecture, content);
        return questionsRepository.save(questions);
    }

    public void update(Question questions, String content){
        questions.update(content);
    }

    public void delete(Question questions){
        questionsRepository.delete(questions);
    }
}
