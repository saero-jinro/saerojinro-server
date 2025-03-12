package question.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.question.application.QuestionQueryService;
import goorm.saerojinro.domain.question.domain.Question;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeQuestionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class QuestionsQueryServiceTest {
    private QuestionQueryService questionsQueryService;

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
        FakeQuestionRepository questionsRepository = new FakeQuestionRepository();
        questionsQueryService = new QuestionQueryService(questionsRepository);

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

        Question questions = Question.create(user,lecture, CONTENT);
        questionsRepository.save(questions);
    }

    public User createUser(){
        return User.builder()
                .id(USER_ID)
                .build();
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

    public Question createQuestions(User user, Lecture lecture, String content){
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
        List<Question> result = questionsQueryService.getAll();

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
        List<Question> result = questionsQueryService.getByLecture(lecture);

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

        List<Question> resultList = questionsQueryService.getByLecture(lecture);
        Question questions = resultList.get(0);
        Long questionsId = questions.getId();

        // when
        Question result = questionsQueryService.getById(questionsId);

        // then
        assertThat(result.getId()).isEqualTo(questionsId);
        assertThat(result.getUser().getId()).isEqualTo(questions.getUser().getId());
        assertThat(result.getLecture().getId()).isEqualTo(questions.getLecture().getId());

    }
}
