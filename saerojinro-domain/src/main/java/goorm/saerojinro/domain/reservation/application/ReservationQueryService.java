package goorm.saerojinro.domain.reservation.application;

import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.reservation.exception.ReservationExistException;
import goorm.saerojinro.domain.reservation.exception.ReservationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {
    private final ReservationRepository reservationRepository;

    public List<Reservation> getAllReservationByUser(Long userId){
        return reservationRepository.findByUserId(userId);
    }

    public Reservation getByUserAndLecture(Long userId, Long lectureId){
        return reservationRepository.findByUserIdAndLectureId(userId, lectureId)
                .orElseThrow(ReservationNotFoundException::new);
    }

    public boolean existsCheckByUserAndStartTime(Long userId, LocalDateTime startTime){
        return reservationRepository.existByUserIdAndStartTime(userId, startTime);
    }

    public List<Reservation> getAllByLectureId(Long lectureId) {
        return reservationRepository.findAllByLectureId(lectureId);
    }

    public void validateReservationByUserAndStartTime(Long userId, LocalDateTime startTime) {
        if (existsCheckByUserAndStartTime(userId, startTime)) {
            throw new ReservationExistException();
        }
    }

}
