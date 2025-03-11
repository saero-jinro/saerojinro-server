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

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
            "FROM Reservation r JOIN r.lecture l " +
            "WHERE r.user.id = :userId AND l.startTime = :startTime")
    boolean existsByUserAndStartTime(@Param("userId") Long userId,
                                     @Param("startTime") LocalDateTime startTime);

    boolean existsByUserAndLecture(User user, Lecture lecture);

    List<Reservation> findAllByUser(User user);

    Optional<Reservation> findByUserAndLecture(User user, Lecture lecture);

	List<Reservation> findAllByLectureId(Long lectureId);
}
