package questions;

import goorm.saerojinro.api.questions.application.QuestionsFacade;
import goorm.saerojinro.api.questions.presentation.request.QuestionsCreateRequest;
import goorm.saerojinro.api.questions.presentation.request.QuestionsUpdateRequest;
import goorm.saerojinro.api.questions.presentation.response.QuestionsCreateResponse;
import goorm.saerojinro.api.questions.presentation.response.QuestionsListResponse;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.questions.application.QuestionsCommandService;
import goorm.saerojinro.domain.questions.application.QuestionsQueryService;
import goorm.saerojinro.domain.questions.exception.QuestionsNotAuthorizedException;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeQuestionsRepository;
import mock.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.domain.BaseRole.ATTENDEE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class QuestionsFacadeTest {
    private QuestionsFacade questionsFacade;

    private FakeUserRepository userRepository;
    private FakeLectureRepository lectureRepository;
    private FakeQuestionsRepository questionsRepository;

    private User admin;
    private User attendee;
    private Lecture lecture;

    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Title";
    private static final String LECTURE_CONTENTS = "Contents";
    private static final String LOCATION = "Location";
    private static final Category CATEGORY = Category.BACKEND;
    private final String CONTENT = "Excellent lecture!";

    @BeforeEach
    void init(){
        lectureRepository = new FakeLectureRepository();
        LectureQueryService lectureQueryService = new LectureQueryService(lectureRepository);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userRepository = new FakeUserRepository();
        UserQueryService userQueryService = new UserQueryService(userRepository, passwordEncoder);

        questionsRepository = new FakeQuestionsRepository();
        QuestionsQueryService  questionsQueryService = new QuestionsQueryService(questionsRepository);
        QuestionsCommandService questionsCommandService = new QuestionsCommandService(questionsRepository);

        questionsFacade = new QuestionsFacade(questionsQueryService, questionsCommandService, userQueryService, lectureQueryService);

        admin = userRepository.save(
                User.builder()
                        .email("email@email.com")
                        .password(passwordEncoder.encode("password1234!"))
                        .name("박민준")
                        .role(ADMIN)
                        .build()
        );

        SecurityContext contextByAdminUser = SecurityContextHolder.getContext();
        contextByAdminUser.setAuthentication(
                new UsernamePasswordAuthenticationToken(admin, admin.getPassword(), admin.getAuthorities())
        );

        attendee = userRepository.save(
                User.builder()
                        .email("nonadminowner@example.com")
                        .password(passwordEncoder.encode("password"))
                        .name("NonAdminOwner")
                        .role(ATTENDEE)
                        .build()
        );

        SecurityContext contextByAttendee = SecurityContextHolder.getContext();
        contextByAttendee.setAuthentication(
                new UsernamePasswordAuthenticationToken(attendee, attendee.getPassword(), attendee.getAuthorities())
        );

        lecture = lectureRepository.save(
                Lecture.builder()
                        .id(LECTURE_ID)
                        .title(LECTURE_TITLE)
                        .contents(LECTURE_CONTENTS)
                        .startTime(LocalDateTime.now().minusHours(2))
                        .endTime(LocalDateTime.now().minusHours(1))
                        .location(LOCATION)
                        .category(CATEGORY)
                        .build()
        );

    }

    @Test
    @DisplayName("getAll 은 전체 질문 조회 response 를 반환한다.")
    public void getAll_Success(){
        // given
        QuestionsCreateRequest createRequest = new QuestionsCreateRequest(CONTENT);
        questionsFacade.create(LECTURE_ID, createRequest);

        // when
        QuestionsListResponse response = questionsFacade.getAll();

        // then
        assertThat(response.questionsList()).hasSize(1);
    }

    @Test
    @DisplayName("getByLecture 은 강의에 해당하는 질문 조회 response 를 반환한다.")
    public void getByLecture_Success(){
        // given
        QuestionsCreateRequest createRequest = new QuestionsCreateRequest(CONTENT);
        questionsFacade.create(LECTURE_ID, createRequest);

        // when
        QuestionsListResponse response = questionsFacade.getByLecture(LECTURE_ID);

        // then
        assertThat(response.questionsList()).hasSize(1);
    }

    @Test
    @DisplayName("create 는 질문 데이터 생성하고, 질문 생성 response 를 반환한다.")
    public void create_Success(){
        // given
        QuestionsCreateRequest createRequest = new QuestionsCreateRequest(CONTENT);

        // when
        QuestionsCreateResponse createResponse = questionsFacade.create(LECTURE_ID, createRequest);

        // then
        assertNotNull(createResponse);


    }

    @Test
    @DisplayName("update 는 질문 데이터를 수정한다.")
    public void update_Success(){
        QuestionsCreateRequest createRequest = new QuestionsCreateRequest(CONTENT);
        QuestionsCreateResponse createResponse = questionsFacade.create(LECTURE_ID, createRequest);
        Long questionId = createResponse.id();

        // when
        String updatedContent = "수정된 질문 입니다.";
        QuestionsUpdateRequest updateRequest = new QuestionsUpdateRequest(updatedContent);
        questionsFacade.update(questionId, updateRequest);

        // then
        QuestionsListResponse response = questionsFacade.getByLecture(LECTURE_ID);
        assertThat(response.questionsList()).hasSize(1);
    }

    @Test
    @DisplayName("update 는 질문 작성자가 아닌 유저가 수정할 때, QuestionsNotAuthorizedException 을 반환한다.")
    public void update_QuestionsNotAuthorizedException(){
        // given
        QuestionsCreateRequest createRequest = new QuestionsCreateRequest(CONTENT);
        QuestionsCreateResponse createResponse = questionsFacade.create(LECTURE_ID, createRequest);
        Long questionId = createResponse.id();

        SecurityContext contextByAdminUser = SecurityContextHolder.getContext();
        contextByAdminUser.setAuthentication(
                new UsernamePasswordAuthenticationToken(admin, admin.getPassword(), admin.getAuthorities())
        );

        String updatedContent = "수정된 질문 입니다.";
        QuestionsUpdateRequest updateRequest = new QuestionsUpdateRequest(updatedContent);

        // then
        assertThrows(QuestionsNotAuthorizedException.class,
                () -> questionsFacade.update(questionId, updateRequest));

    }

    @Test
    @DisplayName("delete 는 질문 데이터를 삭제한다.")
    public void delete_Success(){
        // given
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(attendee, attendee.getPassword(), attendee.getAuthorities())
        );
        QuestionsCreateRequest createRequest = new QuestionsCreateRequest(CONTENT);
        QuestionsCreateResponse createResponse = questionsFacade.create(LECTURE_ID, createRequest);
        Long questionId = createResponse.id();

        // when
        questionsFacade.delete(questionId);

        // then
        QuestionsListResponse response = questionsFacade.getByLecture(LECTURE_ID);
        assertThat(response.questionsList()).isEmpty();
    }

    @Test
    @DisplayName("delete 는 어드민인 경우 질문 작성자가 아니어도 삭제가 가능하다.")
    public void deleteByAdmin_Success(){
        // given
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(attendee, attendee.getPassword(), attendee.getAuthorities())
        );
        QuestionsCreateRequest createRequest = new QuestionsCreateRequest(CONTENT);
        QuestionsCreateResponse createResponse = questionsFacade.create(LECTURE_ID, createRequest);
        Long questionId = createResponse.id();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(admin, admin.getPassword(), admin.getAuthorities())
        );

        // when
        questionsFacade.delete(questionId);

        // then
        QuestionsListResponse response = questionsFacade.getByLecture(LECTURE_ID);
        assertThat(response.questionsList()).isEmpty();
    }

    @Test
    @DisplayName("delete 는 질문 작성자가 아닌 유저가 삭제할 때, QuestionsNotAuthorizedException 을 반환한다.")
    public void delete_QuestionsNotAuthorizedException_NotMatchedUser(){
        // given
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(admin, admin.getPassword(), admin.getAuthorities())
        );
        QuestionsCreateRequest createRequest = new QuestionsCreateRequest(CONTENT);
        QuestionsCreateResponse createResponse = questionsFacade.create(LECTURE_ID, createRequest);
        Long questionId = createResponse.id();

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(attendee, attendee.getPassword(), attendee.getAuthorities())
        );

        // then
        assertThrows(QuestionsNotAuthorizedException.class,
                () -> questionsFacade.delete(questionId));
    }

}
