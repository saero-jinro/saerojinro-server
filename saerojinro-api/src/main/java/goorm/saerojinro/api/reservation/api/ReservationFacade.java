package goorm.saerojinro.api.reservation.api;

import goorm.saerojinro.api.reservation.presentation.response.ReservationCancelResponse;
import goorm.saerojinro.api.reservation.presentation.response.ReservationCreateResponse;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.application.ReservationCommandService;
import goorm.saerojinro.domain.reservation.application.ReservationQueryService;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReservationFacade {
    private final UserQueryService userQueryService;
    private final LectureQueryService lectureQueryService;
    private final ReservationQueryService reservationQueryService;
    private final ReservationCommandService reservationCommandService;

    @Transactional
    public ReservationCreateResponse create(Long userId, Long lectureId) {
        User user = getUser(userId);
        Lecture lecture = getLecture(lectureId);

        Reservation reservation = reservationCommandService.create(user, lecture);

        return ReservationCreateResponse.builder()
                .reservationId(reservation.getId())
                .build();
    }

    @Transactional
    public ReservationCancelResponse cancel(Long userId, Long lectureId){
        User user = getUser(userId);
        Lecture lecture = getLecture(lectureId);

        Reservation reservation = reservationQueryService.getByUserAndLecture(user, lecture);
        reservationCommandService.cancel(reservation);

        return ReservationCancelResponse.builder()
                .reservationId(reservation.getId())
                .build();
    }

    private User getUser(Long userId) {
        return userQueryService.getById(userId);
    }

    private Lecture getLecture(Long lectureId) {
        return lectureQueryService.getByLectureId(lectureId);
    }
}
