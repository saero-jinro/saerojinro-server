package reservation;

import goorm.saerojinro.api.reservation.api.ReservationFacade;
import goorm.saerojinro.api.reservation.presentation.response.ReservationCancelResponse;
import goorm.saerojinro.api.reservation.presentation.response.ReservationCreateResponse;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.reservation.application.ReservationCommandService;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeReservationRepository;
import mock.repository.FakeUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationFacadeTest {

    private ReservationFacade reservationFacade;

    private static final Long USER_ID = 1L;
    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Title";
    private static final String LECTURE_CONTENTS = "Contents";
    private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
    private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
    private static final String LOCATION = "Location";
    private static final Category CATEGORY = Category.BACKEND;
    private static final LectureStatus STATUS = LectureStatus.PENDING_APPROVAL;

    @BeforeEach
    public void init(){
        FakeReservationRepository reservationRepository = new FakeReservationRepository();
        FakeUserRepository userRepository = new FakeUserRepository();
        FakeLectureRepository lectureRepository = new FakeLectureRepository();
        ReservationQueryService reservationQueryService = new ReservationQueryService(reservationRepository);

        reservationFacade = new ReservationFacade(
                new UserQueryService(userRepository, new BCryptPasswordEncoder()),
                new LectureQueryService(lectureRepository),
                reservationQueryService,
                new ReservationCommandService(reservationRepository, reservationQueryService)
        );

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
                .lectureStatus(STATUS)
                .build();

        userRepository.save(user);
        lectureRepository.save(lecture);
    }

    @Test
    @DisplayName("create 는 예약 정보를 생성할 수 있다.")
    public void create(){
        // given
        // when
        ReservationCreateResponse response = reservationFacade.create(USER_ID, LECTURE_ID);

        // then
        assertNotNull(response);
        assertTrue(response.id() > 0);
    }

    @Test
    @DisplayName("cancel 은 기존에 저장된 예약 정보를 삭제한다.")
    public void cancel(){
        // given
        ReservationCreateResponse createResponse = reservationFacade.create(USER_ID, LECTURE_ID);

        // when
        ReservationCancelResponse cancelResponse = reservationFacade.cancel(USER_ID, LECTURE_ID);

        // then
        assertNotNull(cancelResponse);
        assertThat(cancelResponse.id()).isEqualTo(createResponse.id());
    }

}
