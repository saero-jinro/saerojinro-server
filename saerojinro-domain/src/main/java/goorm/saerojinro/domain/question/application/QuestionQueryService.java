package goorm.saerojinro.domain.question.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.question.domain.Question;
import goorm.saerojinro.domain.question.domain.QuestionRepository;
import goorm.saerojinro.domain.question.exception.QuestionNotAuthorizedException;
import goorm.saerojinro.domain.question.exception.QuestionNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;

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

    public void validateByUserId(Question question, Long userId){
        if (!question.getUser().getId().equals(userId)){
            throw new QuestionNotAuthorizedException();
        }
    }

    public void validateByUserRoleAndUserId(User user, Question question){
        if(!(user.getRole().equals(ADMIN) || question.getUser().getId().equals(user.getId()))){
            throw new QuestionNotAuthorizedException();
        }
    }
}
