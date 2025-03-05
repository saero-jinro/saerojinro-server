package goorm.saerojinro.domain.reservation.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.reservation.exception.ReservationExistException;
import goorm.saerojinro.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ReservationQueryService reservationQueryService;

    public Reservation create(User user, Lecture lecture){
        if(reservationQueryService.existsCheck(user, lecture)){
            throw new ReservationExistException();
        }
        Reservation reservation = Reservation.createReservation(user, lecture);
        return reservationRepository.save(reservation);
    }

    public void cancel(Reservation reservation){
        reservationRepository.delete(reservation);
    }
}
