package goorm.saerojinro.infra.repository.impl;

import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.infra.repository.jpa.ReservationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {
    private final ReservationJpaRepository reservationJpaRepository;

    @Override
    public boolean existByUserIdAndStartTime(Long userId, LocalDateTime startTime) {
        return reservationJpaRepository.existsByUserIdAndStartTime(userId, startTime);
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return reservationJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Reservation> findByUserIdAndLectureId(Long userId, Long lectureId) {
        return reservationJpaRepository.findByUserIdAndLectureId(userId, lectureId);
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

    @Override
    public int countByLectureId(Long lectureId) {
        return reservationJpaRepository.countByLectureId(lectureId);
    }
}
