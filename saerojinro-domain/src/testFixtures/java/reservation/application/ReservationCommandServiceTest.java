package reservation.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.reservation.application.ReservationCommandService;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.reservation.exception.ReservationExistException;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationCommandServiceTest {
    ReservationCommandService reservationCommandService;
    ReservationQueryService reservationQueryService;

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
    void init(){
        ReservationRepository reservationRepository = new FakeReservationRepository();
        reservationQueryService = new ReservationQueryService(reservationRepository);
        reservationCommandService = new ReservationCommandService(
                reservationRepository, reservationQueryService);
    }

    private User createUser() {
        return User.builder()
                .id(USER_ID)
                .build();
    }

    private Lecture createLecture() {
        return Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .location(LOCATION)
                .category(CATEGORY)
                .lectureStatus(STATUS)
                .build();
    }

    @Test
    @DisplayName("create 는 예약 정보를 생성 해 저장한다.")
    public void create_Success(){
        // given
        User user = createUser();
        Lecture lecture = createLecture();

        // when
        Reservation reservation = reservationCommandService.create(user, lecture);

        // then
        assertThat(reservation).isNotNull();
        assertThat(reservation.getUser().getId()).isEqualTo(USER_ID);
        assertThat(reservation.getLecture().getId()).isEqualTo(LECTURE_ID);
    }

    @Test
    @DisplayName("create 는 동일한 예약 정보가 존재할 때, ReservationExistException 예외를 던진다.")
    public void create_ReservationExistException(){
        // given
        User user = createUser();
        Lecture lecture = createLecture();

        // when
        reservationCommandService.create(user, lecture);

        // then
        assertThrows(ReservationExistException.class,
                () -> reservationCommandService.create(user, lecture));
    }

    @Test
    @DisplayName("cancel 은 등록된 예약을 취소한다.")
    public void cancel_Success(){
        // given
        User user = createUser();
        Lecture lecture = createLecture();

        Reservation reservation = reservationCommandService.create(user, lecture);

        // when
        reservationCommandService.cancel(reservation);

        // then
        boolean exists = reservationQueryService.existsCheck(user, lecture);
        assertThat(exists).isFalse();
    }


}
