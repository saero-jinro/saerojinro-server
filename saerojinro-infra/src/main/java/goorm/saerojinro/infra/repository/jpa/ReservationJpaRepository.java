package goorm.saerojinro.infra.repository.jpa;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
    boolean existsByUserAndLecture(User user, Lecture lecture);

    List<Reservation> findAllByUser(User user);

    Optional<Reservation> findByUserAndLecture(User user, Lecture lecture);

    void delete(Reservation reservation);

	List<Reservation> findAllByLectureId(Long lectureId);
}
