package reservation.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.reservation.exception.ReservationNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationQueryServiceTest {
    private ReservationQueryService reservationQueryService;

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

        Reservation reservation = Reservation.createReservation(user, lecture);
        reservationRepository.save(reservation);
    }

    private User createUser(Long id) {
        return User.builder().id(id).build();
    }

    private Lecture createLecture(Long id) {
        return Lecture.builder()
                .id(id)
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
    @DisplayName("getAllReservationByUser 는 유저에 해당하는 모든 예약 정보를 조회 할 수 있다.")
    public void getAllReservationByUser_Success(){
        //given
        User user = createUser(USER_ID);

        //when
        List<Reservation> findReservations = reservationQueryService.getAllReservationByUser(
                user);

        //then
        assertThat(findReservations)
                .isNotNull()
                .hasSize(1);

        Reservation reservation = findReservations.get(0);
        assertThat(reservation.getUser().getId()).isEqualTo(USER_ID);
        assertThat(reservation.getLecture().getId()).isEqualTo(LECTURE_ID);

    }

    @Test
    @DisplayName("getByUserAndLecture 는 해당하는 유저와 강의에 대한 예약 정보를 조회 할 수 있다")
    public void getByUserAndLecture_Success(){
        //given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);

        //when
        Reservation findReservation = reservationQueryService.getByUserAndLecture(
                user, lecture);

        //then
        assertThat(findReservation.getUser().getId()).isEqualTo(USER_ID);
        assertThat(findReservation.getLecture().getId()).isEqualTo(LECTURE_ID);

    }

    @Test
    @DisplayName("getByUserAndLecture 는 해당하는 예약 정보가 없을 시, ReservationNotFoundException 예외를 발생 시킨다.")
    public void getByUserAndLecture_ReservationNotFoundException(){
        //given
        User user = createUser(2L);
        Lecture lecture = createLecture(LECTURE_ID);

        //then
        assertThrows(ReservationNotFoundException.class,
                () -> reservationQueryService.getByUserAndLecture(user, lecture));

    }

    @Test
    @DisplayName("existsCheck 는 해당하는 유저와 강의에 대한 예약 정보가 존재하는지 확인 할 수 있다.")
    public void existsCheck_Success(){
        //given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);

        //when
        boolean isExist = reservationQueryService.existsCheck(user, lecture);

        //then
        assertThat(isExist).isTrue();
    }

    @Test
    @DisplayName("getAllByLectureId 는 Lecture 에 예약한 예약 정보를 반환할 수 있다.")
    public void getAllByLectureId_Success(){
        // given

        // when
        List<Reservation> reservationList = reservationQueryService.getAllByLectureId(LECTURE_ID);

        // then
        assertEquals(1, reservationList.size());
        assertEquals(1L, reservationList.get(0).getLecture().getId());
        assertEquals(LECTURE_TITLE, reservationList.get(0).getLecture().getTitle());
        assertEquals(LECTURE_CONTENTS, reservationList.get(0).getLecture().getContents());
    }
}
