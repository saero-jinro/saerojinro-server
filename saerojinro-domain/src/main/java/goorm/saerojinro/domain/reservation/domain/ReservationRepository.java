package goorm.saerojinro.domain.reservation.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    boolean existByUserIdAndStartTime(Long userId, LocalDateTime startTime);

    List<Reservation> findByUserId(Long userId);

    Optional<Reservation> findByUserIdAndLectureId(Long userId, Long lectureId);

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);

	List<Reservation> findAllByLectureId(Long lectureId);

    int countByLectureId(Long lectureId);
}
