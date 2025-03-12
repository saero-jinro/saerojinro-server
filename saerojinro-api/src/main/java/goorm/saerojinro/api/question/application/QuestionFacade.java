package goorm.saerojinro.api.question.application;

import goorm.saerojinro.api.question.presentation.request.QuestionCreateRequest;
import goorm.saerojinro.api.question.presentation.request.QuestionUpdateRequest;
import goorm.saerojinro.api.question.presentation.response.QuestionCreateResponse;
import goorm.saerojinro.api.question.presentation.response.QuestionListResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.question.application.QuestionCommandService;
import goorm.saerojinro.domain.question.application.QuestionQueryService;
import goorm.saerojinro.domain.question.domain.Question;
import goorm.saerojinro.domain.question.exception.QuestionNotAuthorizedException;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static goorm.saerojinro.common.domain.BaseRole.*;

@Component
@RequiredArgsConstructor
public class QuestionFacade {
    private final QuestionQueryService questionsQueryService;
    private final QuestionCommandService questionCommandService;
    private final UserQueryService userQueryService;
    private final LectureQueryService lectureQueryService;

    @Transactional(readOnly = true)
    public QuestionListResponse getAll(){
        List<Question> questionsList = questionsQueryService.getAll();

        return QuestionListResponse.from(questionsList);
    }

    @Transactional(readOnly = true)
    public QuestionListResponse getByLecture(Long lectureId){
        Lecture lecture = lectureQueryService.getById(lectureId);
        List<Question> questionsList = questionsQueryService.getByLecture(lecture);

        return QuestionListResponse.from(questionsList);
    }

    @Transactional
    public QuestionCreateResponse create(Long lectureId, QuestionCreateRequest request){
        User user = userQueryService.me();
        Lecture lecture = lectureQueryService.getById(lectureId);

        Question questions = questionCommandService.create(user, lecture, request.content());

        return QuestionCreateResponse.from(questions.getId());
    }

    @Transactional
    public void update(Long questionsId, QuestionUpdateRequest request){
        Question questions = questionsQueryService.getById(questionsId);
        User user = userQueryService.me();

        if (!questions.getUser().getId().equals(user.getId())){
            throw new QuestionNotAuthorizedException();
        }

        questions.update(request.content());
    }

    @Transactional
    public void delete(Long questionsId){
        Question questions = questionsQueryService.getById(questionsId);
        User user = userQueryService.me();

        if(!(user.getRole().equals(ADMIN) || questions.getUser().getId().equals(user.getId()))){
            throw new QuestionNotAuthorizedException();
        }

        questionCommandService.delete(questions);
    }
}
