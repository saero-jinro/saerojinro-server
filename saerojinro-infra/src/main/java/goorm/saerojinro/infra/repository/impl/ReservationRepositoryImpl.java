package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.infra.repository.jpa.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public boolean existByUserAndLecture(User user, Lecture lecture){
        return reservationJpaRepository.existsByUserAndLecture(user, lecture);
    }
    @Override
    public boolean existByUserAndStartTime(User user, Lecture lecture) {
        return reservationJpaRepository.existsByUserAndStartTime(user.getId(), lecture.getStartTime());
    }

    @Override
    public List<Reservation> findByUser(User user) {
        return reservationJpaRepository.findAllByUser(user);
    }

    @Override
    public Optional<Reservation> findByUserAndLecture(User user, Lecture lecture) {
        return reservationJpaRepository.findByUserAndLecture(user, lecture);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationJpaRepository.save(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        reservationJpaRepository.delete(reservation);
    }

    @Override
    public List<Reservation> findAllByLectureId(Long lectureId) {
        return reservationJpaRepository.findAllByLectureId(lectureId);
    }
}
