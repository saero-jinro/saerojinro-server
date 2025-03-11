package goorm.saerojinro.domain.reservation.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.reservation.exception.ReservationNotFoundException;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {
    private final ReservationRepository reservationRepository;

    public List<Reservation> getAllReservationByUser(User user){
        return reservationRepository.findByUser(user);
    }

    public Reservation getByUserAndLecture(User user, Lecture lecture){
        return reservationRepository.findByUserAndLecture(user, lecture)
                .orElseThrow(ReservationNotFoundException::new);
    }

    public boolean existsCheck(User user, Lecture lecture){
        return reservationRepository.existByUserAndLecture(user, lecture);
    }
    public boolean existsCheckByStartTime(User user, Lecture lecture){
        return reservationRepository.existByUserAndStartTime(user, lecture);
    }

    public List<Reservation> getAllByLectureId(Long lectureId) {
        return reservationRepository.findAllByLectureId(lectureId);
    }
}
