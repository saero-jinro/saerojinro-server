package question.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.question.application.QuestionQueryService;
import goorm.saerojinro.domain.question.domain.Question;
import goorm.saerojinro.domain.question.exception.QuestionNotAuthorizedException;
import goorm.saerojinro.domain.question.exception.QuestionNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeQuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static goorm.saerojinro.common.domain.BaseRole.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuestionsQueryServiceTest {
    private QuestionQueryService questionQueryService;

    private static final Long USER_ID = 1L;
    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Title";
    private static final String LECTURE_CONTENTS = "Contents";
    private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
    private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
    private static final String LOCATION = "Location";
    private static final Category CATEGORY = Category.BACKEND;

    private static final String CONTENT = "사전 질문 입니다";

    @BeforeEach
    void init(){
        FakeQuestionRepository questionRepository = new FakeQuestionRepository();
        questionQueryService = new QuestionQueryService(questionRepository);

        User user = User.builder()
                .id(USER_ID)
                .build();

        Lecture lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .location(LOCATION)
                .category(CATEGORY)
                .build();

        Question question = Question.create(user,lecture, CONTENT);
        questionRepository.save(question);
    }

    public Lecture createLecture(){
        return Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .location(LOCATION)
                .category(CATEGORY)
                .build();
    }

    public Question createQuestion(User user, Lecture lecture, String content){
        return Question.builder()
                .user(user)
                .lecture(lecture)
                .content(content)
                .build();
    }

    @Test
    @DisplayName("getAll 은 모든 질문 데이터를 조회합니다.")
    public void getAll_Success(){
        // when
        List<Question> result = questionQueryService.getAll();

        // then
        Assertions.assertNotNull(result);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getUser().getId()).isEqualTo(USER_ID);
        assertThat(result.get(0).getLecture().getId()).isEqualTo(LECTURE_ID);
    }

    @Test
    @DisplayName("getByLecture 은 강의에 해당하는 질문 데이터를 조회합니다.")
    public void getByLecture_Success(){
        // given
        Lecture lecture = createLecture();

        // when
        List<Question> result = questionQueryService.getByLecture(lecture);

        // then
        Assertions.assertNotNull(result);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getLecture().getId()).isEqualTo(LECTURE_ID);

    }

    @Test
    @DisplayName("getById 는 질문 아이디로 질문 데이터를 조회 합니다.")
    public void getById_Success(){
        // given
        Lecture lecture = createLecture();

        List<Question> resultList = questionQueryService.getByLecture(lecture);
        Question questions = resultList.get(0);
        Long questionsId = questions.getId();

        // when
        Question result = questionQueryService.getById(questionsId);

        // then
        assertThat(result.getId()).isEqualTo(questionsId);
        assertThat(result.getUser().getId()).isEqualTo(questions.getUser().getId());
        assertThat(result.getLecture().getId()).isEqualTo(questions.getLecture().getId());

    }

    @Test
    @DisplayName("getById 는 질문 아이디에 해당하는 데이터가 없을시 QuestionNotFoundException 를 던진다..")
    public void getById_QuestionNotFoundException(){
        // then
        Assertions.assertThrows(QuestionNotFoundException.class, () ->
                questionQueryService.getById(2L));

    }

    @Test
    @DisplayName("validateByUserId 는 질문의 유저 아이디와, 요청 유저 아이디가 같을 경우 예외를 던지지 않는다.")
    public void validateByUserId_Success(){
        // given
        Question question = questionQueryService.getById(USER_ID);

        // then
        assertDoesNotThrow(() ->
                questionQueryService.validateByUserId(question, USER_ID));
    }

    @Test
    @DisplayName("validateByUserId 는 질문의 유저 아이디와, 요청 유저 아이디가 다를 경우 예외를 던진다.")
    public void validateByUserId_QuestionNotAuthorizedException(){
        // given
        Question question = questionQueryService.getById(USER_ID);

        // then
        assertThrows(QuestionNotAuthorizedException.class, () ->
                questionQueryService.validateByUserId(question, 2L)
        );
    }

    @Test
    @DisplayName("validateByUserRoleAndUserId 는 유저의 롤이 admin 인 경우 항상 예외를 던지지 않는다.")
    public void validateByUserRoleAndUserId_Success(){
        // given
        User user = User.builder()
                .id(100L)
                .role(ADMIN)
                .build();

        Question question = questionQueryService.getById(USER_ID);

        // then
        assertDoesNotThrow(() ->
                questionQueryService.validateByUserRoleAndUserId(user, question));
    }

    @Test
    @DisplayName("validateByUserRoleAndUserId 는 유저 아이디와 질문 데이터의 유저 아이디가 같은 경우 예외를 던지지 않는다.")
    public void validateByUserRoleAndUserId_Success_UserId(){
        // given
        User user = User.builder()
                .id(USER_ID)
                .role(ATTENDEE)
                .build();

        Question question = questionQueryService.getById(USER_ID);

        // then
        assertDoesNotThrow(() ->
                questionQueryService.validateByUserRoleAndUserId(user, question));
    }

    @Test
    @DisplayName("validateByUserRoleAndUserId 는 유저 아이디와 질문 데이터의 유저 아이디가 다를 경우 예외를 던진다.")
    public void validateByUserRoleAndUserId_QuestionsNotAuthorizedException(){
        // given
        User user = User.builder()
                .id(3L)
                .role(ATTENDEE)
                .build();

        Question question = questionQueryService.getById(USER_ID);

        // then
        assertThrows(QuestionNotAuthorizedException.class, () ->
                questionQueryService.validateByUserRoleAndUserId(user, question));
    }

}
