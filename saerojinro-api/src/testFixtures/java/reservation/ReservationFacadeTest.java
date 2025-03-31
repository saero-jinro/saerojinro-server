package reservation;

import goorm.saerojinro.api.reservation.application.ReservationFacade;
import goorm.saerojinro.api.reservation.presentation.response.ReservationCreateResponse;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.logevent.application.LogEventService;
import goorm.saerojinro.domain.reservation.application.ReservationCommandService;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.exception.ReservationExistException;
import goorm.saerojinro.domain.reservation.exception.ReservationFullException;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.producer.FakeLogEventProducer;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeLogEventRepository;
import mock.repository.FakeReservationRepository;
import mock.repository.FakeUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationFacadeTest {
    private ReservationFacade reservationFacade;
    private ReservationQueryService reservationQueryService;

    private User user;
    private Lecture lecture;
    private Lecture anotherLecture;

    private static final Long USER_ID = 1L;
    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Title";
    private static final String LECTURE_CONTENTS = "Contents";
    private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
    private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
    private static final String LOCATION = "Location";
    private static final Category CATEGORY = Category.BACKEND;

    @BeforeEach
    public void init(){
        FakeReservationRepository reservationRepository = new FakeReservationRepository();
        FakeUserRepository userRepository = new FakeUserRepository();
        FakeLectureRepository lectureRepository = new FakeLectureRepository();
        FakeLogEventProducer fakeEventLogProducer = new FakeLogEventProducer();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        UserQueryService userQueryService = new UserQueryService(userRepository, passwordEncoder);
        LectureQueryService lectureQueryService = new LectureQueryService(lectureRepository);
        LogEventService logEventService = new LogEventService(new FakeLogEventRepository(), userQueryService, lectureQueryService, fakeEventLogProducer);

        reservationQueryService = new ReservationQueryService(reservationRepository);
        reservationFacade = new ReservationFacade(
                userQueryService,
                lectureQueryService,
                reservationQueryService,
                new ReservationCommandService(reservationRepository),
                logEventService
        );

        user = User.builder()
                .id(USER_ID)
                .email("email@email.com")
                .password(passwordEncoder.encode("password1234!"))
                .name("박민준")
                .role(ADMIN)
                .build();

        SecurityContext contextByAdminUser = SecurityContextHolder.getContext();
        contextByAdminUser.setAuthentication(
                new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
        );

        lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .maxCapacity(1L)
                .location(LOCATION)
                .category(CATEGORY)
                .build();

        anotherLecture = Lecture.builder()
                .id(2L)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .maxCapacity(1L)
                .location(LOCATION)
                .category(CATEGORY)
                .build();

        userRepository.save(user);
        lectureRepository.save(lecture);
        lectureRepository.save(anotherLecture);
    }

    @Test
    @DisplayName("create 는 예약 정보를 생성할 수 있다.")
    public void create(){
        // when
        ReservationCreateResponse response = reservationFacade.create(lecture.getId());

        // then
        assertNotNull(response);
        assertTrue(response.id() > 0);
    }

    @Test
    @DisplayName("create 는 예약하려는 강의의 시작 시간에 해당하는 다른 예약이 있을 시 ReservationExistException을 반환 합니다.")
    public void create_ReservationExistException(){
        // given
        reservationFacade.create(LECTURE_ID);

        // when
        Assertions.assertThrows(ReservationExistException.class,
                () -> reservationFacade.create(2L));
    }

    @Test
    @DisplayName("create 는 예약하려는 강의의 남은 자리가 없을 시 ReservationFullException을 반환 합니다.")
    public void create_ReservationFullException(){
        // given
        reservationFacade.create(LECTURE_ID);

        // when
        Assertions.assertThrows(ReservationFullException.class,
                () -> reservationFacade.create(LECTURE_ID));
    }


    @Test
    @DisplayName("cancel 은 기존에 저장된 예약 정보를 삭제한다.")
    public void cancel(){
        // given
        reservationFacade.create(LECTURE_ID);

        // when
        reservationFacade.cancel(LECTURE_ID);

        // then
        assertThat(reservationQueryService.getAllByLectureId(LECTURE_ID).size()).isEqualTo(0);
    }
}
