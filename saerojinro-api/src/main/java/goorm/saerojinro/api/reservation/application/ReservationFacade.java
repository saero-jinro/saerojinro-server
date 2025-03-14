package goorm.saerojinro.api.reservation.application;

import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_RESERVATION_FAIL;
import static goorm.saerojinro.domain.logevent.domain.enums.LogEventType.LECTURE_RESERVATION_SUCCESS;

import java.time.LocalDateTime;

import goorm.saerojinro.api.reservation.presentation.response.ReservationCreateResponse;
import goorm.saerojinro.common.exception.CustomException;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.logevent.domain.LogEventProducer;
import goorm.saerojinro.domain.logevent.domain.dto.RedisLogEvent;
import goorm.saerojinro.domain.logevent.domain.enums.LogEventType;
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
    private final LogEventProducer logEventProducer;

    @Transactional
    public ReservationCreateResponse create(Long lectureId) {
        User user = userQueryService.me();
        Lecture lecture = lectureQueryService.getById(lectureId);
        LogEventType logEventType;

        try {
            reservationQueryService.validateReservationByUserAndStartTime(
                user.getId(),
                lecture.getStartTime()
            );
            Reservation reservation = reservationCommandService.create(user, lecture);

            logEventType = LECTURE_RESERVATION_SUCCESS;
            RedisLogEvent redisLogEvent = RedisLogEvent.of(
                user.getId(),
                lecture.getId(),
                logEventType,
                lecture.getCategory(),
                LocalDateTime.now()
            );
            logEventProducer.sendMessage(redisLogEvent);

            return ReservationCreateResponse.from(reservation);
        } catch (CustomException e) {
            logEventType = LECTURE_RESERVATION_FAIL;
            RedisLogEvent redisLogEvent = RedisLogEvent.of(
                user.getId(),
                lecture.getId(),
                logEventType,
                lecture.getCategory(),
                LocalDateTime.now()
            );
            logEventProducer.sendMessage(redisLogEvent);

            throw e;
        }
    }

    @Transactional
    public void cancel(Long lectureId){
        User user = userQueryService.me();

        Reservation reservation = reservationQueryService.getByUserAndLecture(
            user.getId(),
            lectureId
        );
        reservationCommandService.cancel(reservation);
    }

}
