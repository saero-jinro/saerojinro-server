package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT COUNT(r) > 0 FROM Reservation r " +
            "WHERE r.user.id = :userId " +
            "AND r.lecture.startTime = :startTime")
    boolean existsByUserIdAndStartTime(@Param("userId") Long userId,
                                     @Param("startTime") LocalDateTime startTime);

    List<Reservation> findAllByUserId(Long userId);

    Optional<Reservation> findByUserIdAndLectureId(Long userId, Long lectureId);

	List<Reservation> findAllByLectureId(Long lectureId);

	int countByLectureId(Long lectureId);
}
