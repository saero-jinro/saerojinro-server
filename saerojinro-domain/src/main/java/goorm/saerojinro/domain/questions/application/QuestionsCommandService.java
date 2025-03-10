package goorm.saerojinro.domain.questions.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.questions.domain.Questions;
import goorm.saerojinro.domain.questions.domain.QuestionsRepository;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionsCommandService {
    private final QuestionsRepository questionsRepository;

    public Questions create(User user, Lecture lecture, String content){
        Questions questions = Questions.create(user, lecture , content);
        return questionsRepository.save(questions);
    }

    public void update(Questions questions, String content){
        questions.update(content);
    }

    public void delete(Questions questions){
        questionsRepository.delete(questions);
    }
}
