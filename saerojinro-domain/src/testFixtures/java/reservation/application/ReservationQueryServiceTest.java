package reservation.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.reservation.exception.ReservationExistException;
import goorm.saerojinro.domain.reservation.exception.ReservationNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeReservationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
                .build();

        Reservation reservation = Reservation.create(user, lecture);
        reservationRepository.save(reservation);
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
                .build();
    }

    @Test
    @DisplayName("getAllReservationByUser 는 유저에 해당하는 모든 예약 정보를 조회 할 수 있다.")
    public void getAllReservationByUser_Success(){
        //when
        List<Reservation> findReservations = reservationQueryService.getAllReservationByUser(
                USER_ID);

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
        //when
        Reservation findReservation = reservationQueryService.getByUserAndLecture(
                USER_ID, LECTURE_ID);

        //then
        assertThat(findReservation.getUser().getId()).isEqualTo(USER_ID);
        assertThat(findReservation.getLecture().getId()).isEqualTo(LECTURE_ID);
    }

    @Test
    @DisplayName("getByUserAndLecture 는 해당하는 예약 정보가 없을 시, ReservationNotFoundException 예외를 발생 시킨다.")
    public void getByUserAndLecture_ReservationNotFoundException(){
        //then
        assertThrows(ReservationNotFoundException.class,
                () -> reservationQueryService.getByUserAndLecture(2L, LECTURE_ID));
    }

    @Test
    @DisplayName("existsCheck 는 해당하는 유저와 강의에 대한 예약 정보가 존재하는지 확인 할 수 있다.")
    public void existsCheck_Success(){
        // given
        Lecture lecture = createLecture();

        // when
        boolean isExist = reservationQueryService.existsCheckByUserAndStartTime(USER_ID, lecture.getStartTime());

        // then
        assertThat(isExist).isTrue();
    }

    @Test
    @DisplayName("getAllByLectureId 는 Lecture 에 예약한 예약 정보를 반환할 수 있다.")
    public void getAllByLectureId_Success(){
        // when
        List<Reservation> reservationList = reservationQueryService.getAllByLectureId(LECTURE_ID);

        // then
        assertEquals(1, reservationList.size());
        assertEquals(1L, reservationList.get(0).getLecture().getId());
        assertEquals(LECTURE_TITLE, reservationList.get(0).getLecture().getTitle());
        assertEquals(LECTURE_CONTENTS, reservationList.get(0).getLecture().getContents());
    }

    @Test
    @DisplayName("countByLectureId 는 Lecture 별 예약의 수를 반환할 수 있다.")
    public void countByLectureId_Success(){
        // when
        int count = reservationQueryService.countByLectureId(LECTURE_ID);

        // then
        assertEquals(1, count);
    }

    @Test
    @DisplayName("validateReservationByUserAndStartTime 는 강의 시작 시간이 중복되는 예약이 있을 시, 예외를 던진다.")
    public void valid_ReservationExistException(){
        // given
        Lecture lecture = createLecture();

        // then
        assertThrows(ReservationExistException.class, () ->
                reservationQueryService.validateReservationByUserAndStartTime(USER_ID, lecture.getStartTime())
        );
    }

    @Test
    @DisplayName("validateReservationByUserAndStartTime 는 예약이 없을 경우 예외를 발생시키지 않는다.")
    public void valid_NoReservationExist(){
        // given
        LocalDateTime nonDuplicateStartTime = LocalDateTime.of(2025, 3, 2, 10, 0);

        // then
        assertDoesNotThrow(() ->
                reservationQueryService.validateReservationByUserAndStartTime(USER_ID, nonDuplicateStartTime)
        );
    }
}
