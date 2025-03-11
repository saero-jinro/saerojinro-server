package question.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.questions.application.QuestionsCommandService;
import goorm.saerojinro.domain.questions.domain.Questions;
import goorm.saerojinro.domain.questions.exception.QuestionsNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeQuestionsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class QuestionsCommandServiceTest {
    private QuestionsCommandService questionsCommandService;
    private FakeQuestionsRepository questionsRepository;

    private User user;
    private Lecture lecture;

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
        questionsRepository = new FakeQuestionsRepository();
        questionsCommandService = new QuestionsCommandService(questionsRepository);

        user = User.builder()
                .id(USER_ID)
                .build();

        lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .location(LOCATION)
                .category(CATEGORY)
                .build();
    }

    @Test
    @DisplayName("create 는 질문 데이터를 생성하여 저장 합니다.")
    public void create_Success(){
        // when
        Questions result = questionsCommandService.create(user, lecture, CONTENT);

        // then
        Assertions.assertNotNull(result);
        assertThat(result.getUser().getId()).isEqualTo(USER_ID);
        assertThat(result.getLecture().getId()).isEqualTo(LECTURE_ID);
    }

    @Test
    @DisplayName("update 는 질문 데이터를 수정 합니다.")
    public void update_Success(){
        // given
        Questions questions = questionsCommandService.create(user, lecture, CONTENT);
        String content = "수정된 질문 입니다.";

        // when
        questionsCommandService.update(questions, content);

        // then
        assertThat(questions.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("delete 는 질문 데이터를 삭제 합니다.")
    public void delete_Success(){
        // given
        Questions questions = questionsCommandService.create(user, lecture, CONTENT);

        // when
        questionsCommandService.delete(questions);

        // then
        Assertions.assertThrows(QuestionsNotFoundException.class,
                () -> questionsRepository.findById(questions.getId()));
    }
}
