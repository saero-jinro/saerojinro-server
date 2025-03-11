package goorm.saerojinro.api.questions.application;

import goorm.saerojinro.api.questions.presentation.request.QuestionsCreateRequest;
import goorm.saerojinro.api.questions.presentation.request.QuestionsUpdateRequest;
import goorm.saerojinro.api.questions.presentation.response.QuestionsCreateResponse;
import goorm.saerojinro.api.questions.presentation.response.QuestionsListResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.questions.application.QuestionsCommandService;
import goorm.saerojinro.domain.questions.application.QuestionsQueryService;
import goorm.saerojinro.domain.questions.domain.Questions;
import goorm.saerojinro.domain.questions.exception.QuestionsNotAuthorizedException;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static goorm.saerojinro.common.domain.BaseRole.*;

@Component
@RequiredArgsConstructor
public class QuestionsFacade {
    private final QuestionsQueryService questionsQueryService;
    private final QuestionsCommandService questionsCommandService;
    private final UserQueryService userQueryService;
    private final LectureQueryService lectureQueryService;

    @Transactional(readOnly = true)
    public QuestionsListResponse getAll(){
        List<Questions> questionsList = questionsQueryService.getAll();

        return QuestionsListResponse.from(questionsList);
    }

    @Transactional(readOnly = true)
    public QuestionsListResponse getByLecture(Long lectureId){
        Lecture lecture = lectureQueryService.getByLectureId(lectureId);
        List<Questions> questionsList = questionsQueryService.getByLecture(lecture);

        return QuestionsListResponse.from(questionsList);
    }

    @Transactional
    public QuestionsCreateResponse create(Long lectureId, QuestionsCreateRequest request){
        User user = userQueryService.me();
        Lecture lecture = lectureQueryService.getByLectureId(lectureId);

        Questions questions = questionsCommandService.create(user, lecture, request.content());

        return QuestionsCreateResponse.from(questions.getId());
    }

    @Transactional
    public void update(Long questionsId, QuestionsUpdateRequest request){
        Questions questions = questionsQueryService.getById(questionsId);
        User user = userQueryService.me();

        if (!questions.getUser().getId().equals(user.getId())){
            throw new QuestionsNotAuthorizedException();
        }

        questions.update(request.content());
    }

    @Transactional
    public void delete(Long questionsId){
        Questions questions = questionsQueryService.getById(questionsId);
        User user = userQueryService.me();

        if(!(user.getRole().equals(ADMIN) || questions.getUser().getId().equals(user.getId()))){
            throw new QuestionsNotAuthorizedException();
        }

        questionsCommandService.delete(questions);
    }
}
